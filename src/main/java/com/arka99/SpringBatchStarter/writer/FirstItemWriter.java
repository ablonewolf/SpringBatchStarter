package com.arka99.SpringBatchStarter.writer;

import com.arka99.SpringBatchStarter.models.StudentCSV;
import com.arka99.SpringBatchStarter.models.StudentXML;
import com.arka99.SpringBatchStarter.models.StudentsJSON;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentXML> {
    @Override
    public void write(List<? extends StudentXML> list) throws Exception {
        System.out.println("Inside the Item Writer.");
        list.stream().forEach(System.out::println);
    }
}
