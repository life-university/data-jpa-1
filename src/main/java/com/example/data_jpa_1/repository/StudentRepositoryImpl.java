package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Student;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Student> findStudentCustom() {
        return em
            .createQuery("select s from Student s", Student.class)
            .getResultList();
    }
}
