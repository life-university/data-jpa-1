package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudentJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Student save(Student student) {
        em.persist(student);
        return student;
    }

    public void delete(Student student) {
        em.remove(student);
    }

    public Student find(Long id) {
        return em.find(Student.class, id);
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    public List<Student> findAll() {
        // JPQL (Java Persistence Query Language) query to select all students
        return em.createQuery("select s from Student s", Student.class).getResultList();
    }

    public long count() {
        return em.createQuery("select count(s) from Student s", Long.class).getSingleResult();
    }

    // 학생 이름과 나이로 조회
    public List<Student> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select s from Student s where s.username = :username and s.age > :age", Student.class)
                 .setParameter("username", username)
                 .setParameter("age", age)
                 .getResultList();
    }

}
