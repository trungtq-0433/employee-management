package com.example.employee_management.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeDTO(
	@NotBlank(message = "Name cannot be left blank")
	String name,

	@NotBlank(message = "Email address cannot be left blank")
	@Email(message = "Email is not in the correct format.")
	String email,

	String password,
	Long departmentId
) {}
