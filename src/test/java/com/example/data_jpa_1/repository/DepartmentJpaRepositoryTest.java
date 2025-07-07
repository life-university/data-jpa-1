package com.example.data_jpa_1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.data_jpa_1.entity.Department;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DepartmentJpaRepositoryTest {

    @Autowired
    DepartmentJpaRepository departmentJpaRepository;

    @Test
    void testDepartment() {
        Department department = new Department("departmentA");
        Department savedDepartment = departmentJpaRepository.save(department);
        Department findDepartment = departmentJpaRepository.find(savedDepartment.getId());

        assertThat(findDepartment.getId()).isEqualTo(department.getId());
        assertThat(findDepartment.getName()).isEqualTo(department.getName());
        assertThat(findDepartment).isEqualTo(department);
    }

    @Test
    void crud() {
        Department department1 = new Department("department1");
        Department department2 = new Department("department2");
        departmentJpaRepository.save(department1);
        departmentJpaRepository.save(department2);

        // 단건 조회 검증
        Department findDepartment1 = departmentJpaRepository.findById(department1.getId()).orElseThrow();
        Department findDepartment2 = departmentJpaRepository.findById(department2.getId()).orElseThrow();
        assertThat(findDepartment1).isEqualTo(department1);
        assertThat(findDepartment2).isEqualTo(department2);

        // 전체 조회 검증
        List<Department> all = departmentJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = departmentJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        departmentJpaRepository.delete(department1);
        departmentJpaRepository.delete(department2);

        long deletedCount = departmentJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}