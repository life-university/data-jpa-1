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
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    void testDepartment() {
        System.out.println("departmentRepository = " + departmentRepository);
        System.out.println("departmentRepository.getClass = " + departmentRepository.getClass());

        Department department = new Department("departmentA");
        Department savedDepartment = departmentRepository.save(department);
        Department findDepartment = departmentRepository.findById(savedDepartment.getId()).orElseThrow();

        assertThat(findDepartment.getId()).isEqualTo(department.getId());
        assertThat(findDepartment.getName()).isEqualTo(department.getName());
        assertThat(findDepartment).isEqualTo(department);
    }

    @Test
    void crud() {
        Department department1 = new Department("department1");
        Department department2 = new Department("department2");
        departmentRepository.save(department1);
        departmentRepository.save(department2);

        // 단건 조회 검증
        Department findDepartment1 = departmentRepository.findById(department1.getId()).orElseThrow();
        Department findDepartment2 = departmentRepository.findById(department2.getId()).orElseThrow();
        assertThat(findDepartment1).isEqualTo(department1);
        assertThat(findDepartment2).isEqualTo(department2);

        // 전체 조회 검증
        List<Department> all = departmentRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = departmentRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        departmentRepository.delete(department1);
        departmentRepository.delete(department2);

        long deletedCount = departmentRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

}