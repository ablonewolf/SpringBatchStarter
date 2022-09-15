package com.arka99.SpringBatchStarter.config;

import com.arka99.SpringBatchStarter.listener.FirstJobListener;
import com.arka99.SpringBatchStarter.listener.FirstStepListener;
import com.arka99.SpringBatchStarter.models.StudentCSV;
import com.arka99.SpringBatchStarter.models.StudentXML;
import com.arka99.SpringBatchStarter.models.StudentsJSON;
import com.arka99.SpringBatchStarter.processor.FirstItemProcessor;
import com.arka99.SpringBatchStarter.reader.FirstItemReader;
import com.arka99.SpringBatchStarter.service.FirstTasklet;
import com.arka99.SpringBatchStarter.service.SecondTasklet;
import com.arka99.SpringBatchStarter.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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
    @Autowired
    ResourceLoader resourceLoader;

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
                .<StudentXML,StudentXML>chunk(4)
//                .reader(flatFileItemReader())
//                .reader(jsonItemReader())
                .reader(staxEventItemReader())
//                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<StudentCSV> flatFileItemReader() {

        FlatFileItemReader<StudentCSV> flatFileItemReader = new FlatFileItemReader<>();

        try {
            flatFileItemReader.setResource(new FileSystemResource("InputFiles/students.csv"));
            System.out.println("Inside the flat item reader");
//            flatFileItemReader.setLineMapper(new DefaultLineMapper<>() {
//                {
////                    setting the column to tokenize the value
//                    setLineTokenizer(new DelimitedLineTokenizer(){
//                        {
//                            setNames("ID","First Name","Last Name","Email");
//                        }
//                    });
////                    setting the fields to map the bean
//                    setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCSV>(){
//                        {
//                            setTargetType(StudentCSV.class);
//
//                        }
//                    });
//                }
//            });
            DefaultLineMapper<StudentCSV> defaultLineMapper = new DefaultLineMapper<>();
//            Setting the line tokenizer to separate the attribute values
            DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
            delimitedLineTokenizer.setNames("ID","First Name","Last Name","Email");
            defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
//            Setting the Bean Wrapper to map the beans
            BeanWrapperFieldSetMapper<StudentCSV> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
            fieldSetMapper.setTargetType(StudentCSV.class);
            defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//            Finalizing the line mapper
            flatFileItemReader.setLineMapper(defaultLineMapper);
            flatFileItemReader.setLinesToSkip(1);
        } catch (Exception e) {
            System.out.println("Could not read the file");
        }


        return flatFileItemReader;
    }

    @Bean
    public JsonItemReader<StudentsJSON> jsonItemReader() {
        JsonItemReader<StudentsJSON> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setResource(new FileSystemResource("InputFiles/students.json"));
        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentsJSON.class));
        jsonItemReader.setMaxItemCount(8);
        jsonItemReader.setCurrentItemCount(4);
        return jsonItemReader;
    }
    @Bean
    public StaxEventItemReader<StudentXML> staxEventItemReader() {
        StaxEventItemReader<StudentXML> staxEventItemReader = new StaxEventItemReader<>();
        staxEventItemReader.setResource(new FileSystemResource("InputFiles/students.xml"));
        staxEventItemReader.setFragmentRootElementName("student");
        staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(StudentXML.class);
            }
        });
        return staxEventItemReader;
    }
}
