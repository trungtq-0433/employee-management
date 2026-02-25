package com.example.employee_management.web.config.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {
    private Long id;
	@NotBlank(message = "Name cannot be left blank")
    private String name;
	@NotBlank(message = "Email address cannot be left blank")
	@Email(message = "Email is not in the correct format.")
    private String email;
    private String password;
    private Long departmentId;
}
