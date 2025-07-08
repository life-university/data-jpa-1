package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.NewSave;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NewSaveRepositoryTest {

    @Autowired
    NewSaveRepository newSaveRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void testNewSave() {
        NewSave newSave = new NewSave("pk_1");
        newSaveRepository.save(newSave);

        em.flush();
        em.clear();

        newSaveRepository.findAll().forEach(System.out::println);
    }

}
