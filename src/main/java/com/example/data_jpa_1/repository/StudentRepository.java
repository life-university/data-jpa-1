package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
