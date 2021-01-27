package com.benz.batch.api.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class LoadController {

    private JobLauncher jobLauncher;
    private Job job;

    public LoadController(JobLauncher jobLauncher,Job job)
    {
        this.jobLauncher=jobLauncher;
        this.job=job;
    }

    @GetMapping
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> jobs =
                new HashMap<>();

        jobs.put("time",new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters
                =new JobParameters(jobs);

        JobExecution jobExecution =
                jobLauncher.run(job,jobParameters);

        System.out.printf("JobExecution: %s \n",jobExecution.getStatus());
        System.out.println("Job is running");

        while (jobExecution.isRunning())
        {
            System.out.println(".......");
        }

        return jobExecution.getStatus();

    }
}
