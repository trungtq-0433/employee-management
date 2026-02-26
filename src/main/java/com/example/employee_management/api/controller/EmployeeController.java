package com.example.employee_management.api.controller;

import com.example.employee_management.api.dto.EmployeeDTO;
import com.example.employee_management.core.model.Employee;
import com.example.employee_management.core.service.EmployeeService;
import jakarta.validation.Valid;
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

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> search(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String deptName) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.search(name, deptName));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeDTO dto) {
        Employee employee = employeeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        Employee updatedEmployee = employeeService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }

    @GetMapping("/total")
    public long getTotal() {
        return employeeService.countTotalEmployees();
    }
}
