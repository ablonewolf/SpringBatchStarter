package com.arka99.SpringBatchStarter.controller;

import com.arka99.SpringBatchStarter.models.JobParamsRequest;
import com.arka99.SpringBatchStarter.service.JobService;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/job")
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    JobOperator jobOperator;


    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName, @RequestBody List<JobParamsRequest> jobParamsRequests) {
        jobParamsRequests.forEach(jobParamsRequest -> {
            System.out.println(jobParamsRequest);
        });
        jobService.startJob(jobName, jobParamsRequests);

        return jobName + " started";
    }

    @GetMapping("/stop/{executionID}")
    public String stopJob(@PathVariable long executionID) {
        try {
            jobOperator.stop(executionID);
        } catch (NoSuchJobExecutionException e) {
            System.out.println("No such job exists");
        } catch (JobExecutionNotRunningException e) {
            System.out.println("No job running");
        }
        return "job with id " + executionID + " stopped";
    }
}
