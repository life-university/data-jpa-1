package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Student;
import java.util.List;

public interface StudentRepositoryCustom {

    List<Student> findStudentCustom();

}
