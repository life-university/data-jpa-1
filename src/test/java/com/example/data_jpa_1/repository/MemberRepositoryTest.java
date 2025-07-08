package com.example.data_jpa_1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.data_jpa_1.entity.Member;
import com.example.data_jpa_1.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        Member member = new Member(null, "memberA", null);
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void fetchTest() {
        // member1 -> teamA
        // member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member(null, "member1", teamA);
        Member member2 = new Member(null, "member2", teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // single fetch join
        Member find1 = memberRepository.findById(1L).orElseThrow();
        System.out.println("====================");
        System.out.println("find member = " + find1);
        System.out.println("====================");
        System.out.println("find team = " + find1.getTeam());
        System.out.println("====================");

        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");

        em.flush();
        em.clear();

        // multiple fetch join
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("====================");
            System.out.println("loop find member = " + member);
            System.out.println("====================");
            System.out.println("loop find team = " + member.getTeam());
            System.out.println("====================");
        }

    }

    @Test
    void fetchJoinTest() {
        // member1 -> teamA
        // member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member(null, "member1", teamA);
        Member member2 = new Member(null, "member2", teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findMemberFetchJoin();
        for (Member member : members) {
            System.out.println("====================");
            System.out.println("loop find member = " + member);
            System.out.println("====================");
            System.out.println("loop find team = " + member.getTeam().getClass() +" / "+ member.getTeam());
            System.out.println("====================");
        }

    }

    @Test
    void nativeQueryTest() {
        // given
        memberRepository.save(new Member("member1"));
        memberRepository.save(new Member("member2"));
        memberRepository.save(new Member("member3"));

        em.flush();
        em.clear();

        // when
        Member member = memberRepository.findNativeByUsername("member1");

        // then
        assertThat(member.getUsername()).isEqualTo("member1");
    }

}