package com.arka99.SpringBatchStarter.service;

import com.arka99.SpringBatchStarter.models.Student;
import com.arka99.SpringBatchStarter.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    List<Student> studentList;

    public void saveStudent(Student student) {

        List<Student> students = studentRepository.findAll();
        if (students.contains(student)) {
            System.out.println("This entity already exists " + student);
            return;
        }
        studentRepository.save(student);
        System.out.println("Saving the object " + student);
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public List<Student> restCallToStudents() {
        System.out.println(studentList);
        studentList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        Student[] students = restTemplate.getForObject("http://localhost:8080/api/job/get/students",Student[].class);
        System.out.println(students);
        studentList.addAll(Arrays.asList(students));
        return studentList;
    }
    public Student getStudent() {
        if(studentList == null) {
            restCallToStudents();
        }
        if(studentList!=null && !studentList.isEmpty()) {
            return studentList.remove(0);
        }
        return null;
    }
}
