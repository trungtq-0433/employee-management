package com.example.employee_management.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/login", "/register").permitAll()
					.requestMatchers("/employees/list").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/employees/add", "/employees/edit/**", "/employees/save", "/employees/delete/**").hasRole("ADMIN")
					.anyRequest().authenticated();
			}).formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/employees/list", true)
				.permitAll()
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login")
				.permitAll()
			);


		return http.build();
	}
}
