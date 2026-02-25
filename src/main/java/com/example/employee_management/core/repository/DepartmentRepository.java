package com.example.employee_management.core.repository;

import com.example.employee_management.core.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
