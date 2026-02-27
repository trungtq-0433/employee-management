package com.example.employee_management.core.repository;

import com.example.employee_management.core.model.Employee;
import com.example.employee_management.web.dto.DepartmentStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentName(String deptName);

    @Query("SELECT e.department.name AS departmentName, COUNT(e) AS employeeCount " +
        "FROM Employee e GROUP BY e.department.name")
    List<DepartmentStat> countEmployeesByDepartment();

    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();
}
