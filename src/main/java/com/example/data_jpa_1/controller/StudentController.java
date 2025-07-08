package com.example.data_jpa_1.controller;

import com.example.data_jpa_1.entity.Student;
import com.example.data_jpa_1.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/students/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        return student.getUsername();
    }

    @GetMapping("/students2/{id}")
    public String findMember2(@PathVariable("id") Student student) {
        return student.getUsername();
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            studentRepository.save(new Student("student" + i));
        }
    }


}
