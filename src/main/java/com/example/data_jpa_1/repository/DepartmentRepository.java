package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
