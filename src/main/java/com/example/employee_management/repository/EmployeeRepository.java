package com.example.employee_management.repository;

import com.example.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentName(String deptName);
}
