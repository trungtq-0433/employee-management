package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UtilityService utilityService;
    private final List<Employee> employeeList = new ArrayList<>();

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee save(EmployeeDTO dto) {
        Employee employee = modelMapper.map(dto, Employee.class);

        employee.setPassword(passwordEncoder.encode(dto.password()));

        String code = utilityService.generateEmployeeCode();
        employee.setEmployeeCode(code);

        employeeList.add(employee);
        return employee;
    }
}
