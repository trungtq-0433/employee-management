package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDTO dto) {
        Employee employee = employeeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
}
