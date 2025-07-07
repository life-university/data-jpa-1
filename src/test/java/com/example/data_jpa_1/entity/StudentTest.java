package com.example.data_jpa_1.entity;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StudentTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void testEntity() {
        Department departmentA = new Department("departmentA");
        Department departmentB = new Department("departmentB");
        em.persist(departmentA);
        em.persist(departmentB);

        Student student1 = new Student("student1", 10, departmentA);
        Student student2 = new Student("student2", 12, departmentA);
        Student student3 = new Student("student3", 14, departmentB);
        Student student4 = new Student("student4", 16, departmentB);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(student4);

        em.flush(); // db insert query execution
        em.clear(); // clear the persistence context

        //
        List<Student> students = em.createQuery("select s from Student s", Student.class).getResultList();
        for (Student student : students) {
            System.out.println("student = " + student);
            System.out.println("--> student.department = " + student.getDepartment());
        }

    }
}