package com.example.employee_management.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheTasks {

	@CacheEvict(value = "employeeCount", allEntries = true)
	@Scheduled(fixedRate = 60000)
	public void clearEmployeeCountCache() {
		log.info("Cleaning up employee count cache...");
	}
}
