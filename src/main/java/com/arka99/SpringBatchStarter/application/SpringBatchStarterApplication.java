package com.arka99.SpringBatchStarter.application;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.Collection;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.arka99.SpringBatchStarter.config","com.arka99.SpringBatchStarter.service","com.arka99.SpringBatchStarter.listener","com.arka99.SpringBatchStarter.reader","com.arka99.SpringBatchStarter.writer","com.arka99.SpringBatchStarter.processor","com.arka99.SpringBatchStarter.controller"})
@EnableAsync
//@EnableScheduling
public class SpringBatchStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchStarterApplication.class, args);
	}

}
