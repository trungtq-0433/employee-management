package com.example.employee_management.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmployeeService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UtilityService utilityService;

    public EmployeeService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UtilityService utilityService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.utilityService = utilityService;
    }
}
