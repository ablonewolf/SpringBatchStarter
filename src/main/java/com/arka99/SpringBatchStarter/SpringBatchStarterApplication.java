package com.arka99.SpringBatchStarter;

import com.arka99.SpringBatchStarter.models.Student;
import com.arka99.SpringBatchStarter.service.StudentService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.arka99.SpringBatchStarter.config","com.arka99.SpringBatchStarter.service","com.arka99.SpringBatchStarter.listener","com.arka99.SpringBatchStarter.reader","com.arka99.SpringBatchStarter.writer","com.arka99.SpringBatchStarter.processor","com.arka99.SpringBatchStarter.controller","com.arka99.SpringBatchStarter.repository"})
@EnableAsync
//@EnableScheduling
public class SpringBatchStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchStarterApplication.class, args);
	}
//	@Bean
//	CommandLineRunner runner(StudentService service) {
//		return args -> {
////			service.saveStudent(new Student(1,"Arka","Bhuiyan","arka.bhuiyan@bjitgroup.com"));
////			service.saveStudent(new Student(2,"Farhan","Zaman","farhan.zaman@bjitgroup.com"));
////			service.saveStudent(new Student(3,"Nipa","Howladar","nipa.howladar@bjitgroup.com"));
////			service.saveStudent(new Student(4,"Akif","Azwad","akif.azwad@bjitgroup.com"));
////			service.saveStudent(new Student(5,"Riazul","Rana","riazul.rana@bjitgroup.com"));
////			service.saveStudent(new Student(6,"Zareen","Zia","zareen.zia@bjitgroup.com"));
////			service.saveStudent(new Student(7,"Faiaz","Mursalin","faiaz.mursalin@bjitgroup.com"));
////			service.saveStudent(new Student(8,"Iftekhar","Alam","iftekhar.alam@bjitgroup.com"));
////			service.saveStudent(new Student(9,"Saif","Shohel","saif.shohel@bjitgroup.com"));
////			service.saveStudent(new Student(10,"Somoy","Subandhu","somoy.subandhu@bjitgroup.com"));
////			service.saveStudent(new Student(11,"Arka","Bhuiyan","arkabhuiyan@gmail.com"));
//		};
//	}
}
