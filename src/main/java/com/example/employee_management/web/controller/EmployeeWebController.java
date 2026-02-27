package com.example.employee_management.web.controller;

import com.example.employee_management.core.repository.DepartmentRepository;
import com.example.employee_management.core.repository.EmployeeRepository;
import com.example.employee_management.core.service.EmployeeService;
import com.example.employee_management.web.dto.EmployeeForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeWebController {
	private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;

	@GetMapping("/list")
	public String listEmployees(
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String deptName,
		Model model
	) {
		model.addAttribute("employees", employeeService.search(name, deptName));
		return "employees/list";
	}

	@GetMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public String showAddForm(Model model) {
		model.addAttribute("employeeForm", new EmployeeForm());
		model.addAttribute("departments", departmentRepository.findAll());
		return "employees/form";
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public String saveEmployee(
		@Valid @ModelAttribute("employeeForm") EmployeeForm form,
		BindingResult result,
		Model model,
		RedirectAttributes ra
	) {
		if (result.hasErrors()) {
			model.addAttribute("departments", departmentRepository.findAll());
			return "employees/form";
		}

		employeeService.saveFromForm(form);
		ra.addFlashAttribute("message", "Employee saved successfully!");
		return "redirect:/employees/list";
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String showEditForm(@PathVariable Long id, Model model) {
		model.addAttribute("employeeForm", employeeService.getFormById(id));
		model.addAttribute("departments", departmentRepository.findAll());
		return "employees/form";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteEmployee(@PathVariable Long id, RedirectAttributes ra) {
		employeeService.delete(id);
		ra.addFlashAttribute("message", "Employee deleted successfully!");
		return "redirect:/employees/list";
	}

	@GetMapping("/statistics")
	public String showStatistics(Model model) {
		model.addAttribute("total", employeeRepository.countTotalEmployees());
		model.addAttribute("deptStats", employeeRepository.countEmployeesByDepartment());
		return "employees/statistics";
	}
}
