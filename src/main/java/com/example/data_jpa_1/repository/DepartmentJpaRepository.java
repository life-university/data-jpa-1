package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Department;
import com.example.data_jpa_1.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Department save(Department department) {
        em.persist(department);
        return department;
    }

    public void delete(Department department) {
        em.remove(department);
    }

    public Department find(Long id) {
        return em.find(Department.class, id);
    }

    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(em.find(Department.class, id));
    }

    public List<Department> findAll() {
        // JPQL (Java Persistence Query Language) query to select all students
        return em.createQuery("select d from Department d", Department.class).getResultList();
    }

    public long count() {
        return em.createQuery("select count(d) from Department d", Long.class).getSingleResult();
    }
}
