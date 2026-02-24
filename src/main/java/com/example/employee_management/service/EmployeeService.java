package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Department;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
    }
}
