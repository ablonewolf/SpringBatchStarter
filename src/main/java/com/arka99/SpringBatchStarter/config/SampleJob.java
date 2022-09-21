package com.arka99.SpringBatchStarter.config;

import com.arka99.SpringBatchStarter.listener.FirstJobListener;
import com.arka99.SpringBatchStarter.listener.FirstStepListener;
import com.arka99.SpringBatchStarter.models.Student;
import com.arka99.SpringBatchStarter.models.StudentCSV;
import com.arka99.SpringBatchStarter.models.StudentXML;
import com.arka99.SpringBatchStarter.models.StudentsJSON;
import com.arka99.SpringBatchStarter.processor.FirstItemProcessor;
import com.arka99.SpringBatchStarter.reader.FirstItemReader;
import com.arka99.SpringBatchStarter.service.FirstTasklet;
import com.arka99.SpringBatchStarter.service.SecondTasklet;
import com.arka99.SpringBatchStarter.service.StudentService;
import com.arka99.SpringBatchStarter.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.*;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

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

    @Autowired
    @Qualifier("datasource")
    private DataSource datasource;
    @Autowired
    @Qualifier("universitydatasource")
    private DataSource universitydatasource;
    @Autowired
    private StudentService studentService;



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
                .<StudentCSV,Student>chunk(4)
                .reader(flatFileItemReader())
//                .reader(jsonItemReader())
//                .reader(itemReaderAdapter())
//                .reader(jdbcCursorItemReader())
//                .processor(firstItemProcessor)
//                .writer(firstItemWriter)
//                .writer(flatFileItemWriter())
//                .writer(jsonFileItemWriter())
//                .writer(staxEventItemWriter())
                .writer(jdbcBatchItemWriter())
                .build();
    }

//    CSV Item Reader
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

//    JSON Item reader
    @Bean
    public JsonItemReader<StudentsJSON> jsonItemReader() {
        JsonItemReader<StudentsJSON> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setResource(new FileSystemResource("InputFiles/students.json"));
        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentsJSON.class));
        jsonItemReader.setMaxItemCount(8);
        jsonItemReader.setCurrentItemCount(4);
        return jsonItemReader;
    }

//    XML Item reader
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
//    Database item reader
    @Bean
    public JdbcCursorItemReader<Student> jdbcCursorItemReader() {
        JdbcCursorItemReader<Student> jdbcCursorItemReader = new JdbcCursorItemReader<>();
        jdbcCursorItemReader.setDataSource(universitydatasource);
        jdbcCursorItemReader.setSql("select id, first_name as firstName, last_name as lastName, email from student");
        jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<>() {
            {
                setMappedClass(Student.class);
            }
        });
        return jdbcCursorItemReader;
    }
    @Bean
    public ItemReaderAdapter<Student> itemReaderAdapter(){
        ItemReaderAdapter<Student> itemReaderAdapter = new ItemReaderAdapter<>();
        itemReaderAdapter.setTargetObject(studentService);
        itemReaderAdapter.setTargetMethod("getStudent");
        return itemReaderAdapter;
    }
    @Bean
    public FlatFileItemWriter<Student> flatFileItemWriter() {
        FlatFileItemWriter<Student> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource("OutputFiles/students.csv"));
//        Setting the headers for our csv file
        FlatFileHeaderCallback flatFileHeaderCallback = new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("Id,First Name,Last Name,Email");
            }
        };
        flatFileItemWriter.setHeaderCallback(flatFileHeaderCallback);
//        Setting the line aggregator to write all lines
        DelimitedLineAggregator<Student> lineAggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id","firstName","lastName","email"});
        lineAggregator.setFieldExtractor(fieldExtractor);
//        Adding a footer for our csv file
        FlatFileFooterCallback footerCallback = new FlatFileFooterCallback() {
            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("Created @ " + new Date());
            }
        };
        flatFileItemWriter.setLineAggregator(lineAggregator);
        flatFileItemWriter.setFooterCallback(footerCallback);
        return flatFileItemWriter;
    }

    @Bean
    public JsonFileItemWriter<StudentsJSON> jsonFileItemWriter() {
        Resource resource = new FileSystemResource("OutputFiles/students.json");
        JsonObjectMarshaller<StudentsJSON> objectMarshaller = new JacksonJsonObjectMarshaller<>();
        JsonFileItemWriter<StudentsJSON> jsonFileItemWriter = new JsonFileItemWriter<>(resource,objectMarshaller);
        return jsonFileItemWriter;
    }

    @Bean
    public StaxEventItemWriter<Student> staxEventItemWriter() {
        StaxEventItemWriter<Student> staxEventItemWriter = new StaxEventItemWriter<>();
        staxEventItemWriter.setResource(new FileSystemResource("OutputFiles/students.xml"));
        staxEventItemWriter.setRootTagName("students");
        staxEventItemWriter.setMarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(Student.class);
            }
        });
        return staxEventItemWriter;
    }
    @Bean
    public JdbcBatchItemWriter<Student> jdbcBatchItemWriter() {
        JdbcBatchItemWriter<Student> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(universitydatasource);
        jdbcBatchItemWriter.setSql("insert into student(id, first_name, last_name, email)"
        + "values (:id, :firstName, :lastName, :email)");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return jdbcBatchItemWriter;
    }
}
