package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
