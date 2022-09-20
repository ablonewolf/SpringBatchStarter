package com.arka99.SpringBatchStarter.service;

import com.arka99.SpringBatchStarter.models.Student;
import com.arka99.SpringBatchStarter.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(Student student) {
        List<Student> students = studentRepository.findAll();
        if (students.contains(student)) {
            System.out.println("This entity already exists " + student);
            return;
        }
        studentRepository.save(student);
        System.out.println("Saving the object " + student);
    }
}
