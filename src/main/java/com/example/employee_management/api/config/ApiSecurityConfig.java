package com.example.employee_management.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class ApiSecurityConfig {

	@Bean
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/api/**")
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
