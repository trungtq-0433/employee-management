package com.example.employee_management.core.service;

import com.example.employee_management.api.dto.EmployeeDTO;
import com.example.employee_management.core.model.Department;
import com.example.employee_management.core.model.Employee;
import com.example.employee_management.core.repository.DepartmentRepository;
import com.example.employee_management.core.repository.EmployeeRepository;
import com.example.employee_management.web.dto.EmployeeForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> search(String name, String deptName) {
        if (deptName != null && !deptName.trim().isEmpty()) {
            return employeeRepository.findByDepartmentName(deptName);
        }
        else if (name != null && !name.trim().isEmpty()) {
            return employeeRepository.findByNameContainingIgnoreCase(name);
        }
        else {
            return employeeRepository.findAll();
        }
    }

    @Transactional
    public Employee save(EmployeeDTO dto) {
        Employee employee = modelMapper.map(dto, Employee.class);
        employee.setPassword(passwordEncoder.encode(dto.password()));

        if (dto.departmentId() != null) {
            Department dept = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not exists " + dto.departmentId()));
            employee.setDepartment(dept);
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee not exists " + id));

        modelMapper.map(dto, existing);

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.password()));
        }

        if (dto.departmentId() != null) {
            Department dept = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not exists " + dto.departmentId()));
            existing.setDepartment(dept);
        }

        return employeeRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not exists " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Employee ID: {} has been deleted successfully", id);
    }

    @Transactional
    public Employee saveFromForm(EmployeeForm form) {
        Employee employee;

        if (form.getId() != null) {
            log.info("Updating existing employee {}", form.getId());
            employee = employeeRepository.findById(form.getId())
                .orElseThrow(() -> {
                    log.error("Employee ID {} not found", form.getId());
                    return new EntityNotFoundException("Employee not exists " + form.getId());
                });
        } else {
            log.info("Creating a new employee with email {}", form.getEmail());
            employee = new Employee();
        }

        employee.setName(form.getName());
        employee.setEmail(form.getEmail());

        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(form.getPassword()));
        }

        if (form.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() -> {
                    log.warn("Department {} not found", form.getDepartmentId());
                    return new EntityNotFoundException("Department not exists " + form.getDepartmentId());
                });

            employee.setDepartment(dept);
        } else {
            employee.setDepartment(null);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee successfully saved with ID: {}", savedEmployee.getId());

        return savedEmployee;
    }

    public EmployeeForm getFormById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Employee not exists " + id));

        EmployeeForm form = new EmployeeForm();
        form.setId(employee.getId());
        form.setName(employee.getName());
        form.setEmail(employee.getEmail());
        form.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);

        return form;
    }

    @Cacheable(value = "employeeCount")
    public long countTotalEmployees() {
        log.info("Calculating total employees (Fetching from Database...)");
        return employeeRepository.count();
    }
}
