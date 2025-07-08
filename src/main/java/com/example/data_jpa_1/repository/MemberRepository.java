package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m left join m.team")
    List<Member> findMemberFetchJoin();

    @Query(name = "select * from member where username = ?", nativeQuery = true)
    Member findNativeByUsername(String name);

}
