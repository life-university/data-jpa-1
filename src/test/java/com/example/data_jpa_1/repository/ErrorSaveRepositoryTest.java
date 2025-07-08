package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.ErrorSave;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ErrorSaveRepositoryTest {

    @Autowired
    ErrorSaveRepository errorSaveRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void testStudent() {
        ErrorSave errorSave = new ErrorSave("pk_1");
        errorSaveRepository.save(errorSave);

        em.flush();
        em.clear();

        errorSaveRepository.findAll().forEach(System.out::println);
    }

}
