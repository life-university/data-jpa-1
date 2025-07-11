package com.example.data_jpa_1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.data_jpa_1.entity.Student;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StudentJpaRepositoryTest {

    @Autowired
    StudentJpaRepository studentJpaRepository;

    @Test
    void testStudent() {
        Student student = new Student("studentA");
        Student savedStudent = studentJpaRepository.save(student);
        Student findStudent = studentJpaRepository.find(savedStudent.getId());

        assertThat(findStudent.getId()).isEqualTo(student.getId());
        assertThat(findStudent.getUsername()).isEqualTo(student.getUsername());
        assertThat(findStudent).isEqualTo(student);
    }

    @Test
    void crud() {
        Student student1 = new Student("student1");
        Student student2 = new Student("student2");
        studentJpaRepository.save(student1);
        studentJpaRepository.save(student2);

        // 단건 조회 검증
        Student findStudent1 = studentJpaRepository.findById(student1.getId()).orElseThrow();
        Student findStudent2 = studentJpaRepository.findById(student2.getId()).orElseThrow();
        assertThat(findStudent1).isEqualTo(student1);
        assertThat(findStudent2).isEqualTo(student2);

        // 전체 조회 검증
        List<Student> all = studentJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = studentJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        studentJpaRepository.delete(student1);
        studentJpaRepository.delete(student2);

        long deletedCount = studentJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        studentJpaRepository.save(student1);
        studentJpaRepository.save(student2);

        // when
        List<Student> findStudents = studentJpaRepository.findByUsernameAndAgeGreaterThan("student2", 18);

        // then
        assertThat(findStudents.size()).isEqualTo(1);
        assertThat(findStudents.get(0).getUsername()).isEqualTo("student2");
        assertThat(findStudents.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void bulkUpdate() {
        // given
        for (int i = 0; i < 10; i++) {
            studentJpaRepository.save(new Student("student_" + i, 10 + i));
        }

        // when
        int resultCount = studentJpaRepository.bulkAgePlus(15); //

        // then
        assertThat(resultCount).isEqualTo(5);
    }
}
