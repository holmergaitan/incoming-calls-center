package com.callcenter.configuration;

import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * Main configuration
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.callcenter.app")
@EnableJpaRepositories(basePackages = "com.callcenter.app.jpa")
@EntityScan(basePackages = "com.callcenter.app.model")
@EnableJms
@EnableScheduling
public class CallCenterStartUp {

	/**
	 * Sets up de application
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		SpringApplication.run(CallCenterStartUp.class, args);
	}

	/**
	 * Creates a new TaskExecutor bean usted by scheduled tasks
	 *
	 * @return the task scheduler
	 */
	@Bean
	public TaskScheduler taskExecutor() {

		return new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(10));
	}
}
