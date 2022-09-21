package com.arka99.SpringBatchStarter.processor;

import com.arka99.SpringBatchStarter.models.Student;
import com.arka99.SpringBatchStarter.models.StudentsJSON;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Student, StudentsJSON> {
    @Override
    public StudentsJSON process(Student student) throws Exception {
        System.out.println("Inside the Item Processor.");
        StudentsJSON studentsJSON = new StudentsJSON();
        studentsJSON.setId(student.getId());
        studentsJSON.setFirstName(student.getFirstName());
        studentsJSON.setLastName(student.getLastName());
        studentsJSON.setEmail(student.getEmail());
        return studentsJSON;
    }
}
