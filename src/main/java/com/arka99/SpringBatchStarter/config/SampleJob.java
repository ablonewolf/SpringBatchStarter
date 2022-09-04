package com.arka99.SpringBatchStarter.config;

import com.arka99.SpringBatchStarter.listener.FirstJobListener;
import com.arka99.SpringBatchStarter.listener.FirstStepListener;
import com.arka99.SpringBatchStarter.processor.FirstItemProcessor;
import com.arka99.SpringBatchStarter.reader.FirstItemReader;
import com.arka99.SpringBatchStarter.service.FirstTasklet;
import com.arka99.SpringBatchStarter.service.SecondTasklet;
import com.arka99.SpringBatchStarter.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private FirstJobListener firstJobListener;
    @Autowired
    private FirstStepListener firstStepListener;
    @Autowired
    private FirstTasklet firstTasklet;
    @Autowired
    private SecondTasklet secondTasklet;
    @Autowired
    private FirstItemReader firstItemReader;
    @Autowired
    private FirstItemProcessor firstItemProcessor;
    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First step")
                .tasklet(firstTasklet)
                .listener(firstStepListener)
                .build();
    }
    private Step secondStep() {
        return stepBuilderFactory.get("Second step")
                .tasklet(secondTasklet)
                .build();
    }
//    private Tasklet firstTask() {
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("This is the first tasklet step");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
//    private Tasklet secondTask() {
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("This is the second tasklet step");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("second job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<Integer,Long>chunk(4)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }
}
