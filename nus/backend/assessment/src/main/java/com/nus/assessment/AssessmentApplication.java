package com.nus.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.nus.assessment.service.ContractService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AssessmentApplication {

	@Autowired
    private ContractService contractService;

	@PostConstruct
	public void deployContract() {
		try {
			contractService.deployContract();
		} catch (Exception e) {
			new SimpleAsyncTaskExecutor().execute(() -> System.exit(-1));
		}
		
	}
	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

}
