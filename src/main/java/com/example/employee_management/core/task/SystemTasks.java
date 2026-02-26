package com.example.employee_management.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemTasks {

	@Scheduled(fixedRate = 30000)
	public void reportStatus() {
		log.info("System running");
	}
}
