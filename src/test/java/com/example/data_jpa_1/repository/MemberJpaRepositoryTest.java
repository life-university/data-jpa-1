package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        Member member = new Member(null, "memberA", null);
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void jpaEventBaseEntity() throws InterruptedException {
        // given
        Member member1 = new Member("member1");
        memberJpaRepository.save(member1); // @PrePersist called

        Thread.sleep(1000); // 1 second delay.

        member1.setUsername("member2");

        em.flush(); // @PreUpdate called
        em.clear();

        // when
        Member findMember = memberJpaRepository.find(member1.getId());

        // then
        System.out.println("findMember.getCreatedAt() = " + findMember.getCreatedAt());
        System.out.println("findMember.getUpdatedAt() = " + findMember.getUpdatedAt());
    }

}