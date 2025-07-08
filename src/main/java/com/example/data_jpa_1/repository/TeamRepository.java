package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
