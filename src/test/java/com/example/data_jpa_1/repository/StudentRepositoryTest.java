package com.example.data_jpa_1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.data_jpa_1.dto.StudentDTO;
import com.example.data_jpa_1.entity.Department;
import com.example.data_jpa_1.entity.Student;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testStudent() {
        System.out.println("studentRepository = " + studentRepository);
        System.out.println("studentRepository.getClass = " + studentRepository.getClass());

        Student student = new Student("studentA");
        Student savedStudent = studentRepository.save(student);
        Student findStudent = studentRepository.findById(savedStudent.getId()).orElseThrow();

        assertThat(findStudent.getId()).isEqualTo(student.getId());
        assertThat(findStudent.getUsername()).isEqualTo(student.getUsername());
        assertThat(findStudent).isEqualTo(student);
    }

    @Test
    void crud() {
        Student student1 = new Student("student1");
        Student student2 = new Student("student2");
        studentRepository.save(student1);
        studentRepository.save(student2);

        // 단건 조회 검증
        Student findStudent1 = studentRepository.findById(student1.getId()).orElseThrow();
        Student findStudent2 = studentRepository.findById(student2.getId()).orElseThrow();
        assertThat(findStudent1).isEqualTo(student1);
        assertThat(findStudent2).isEqualTo(student2);

        // 전체 조회 검증
        List<Student> all = studentRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = studentRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        studentRepository.delete(student1);
        studentRepository.delete(student2);

        long deletedCount = studentRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when
        List<Student> findStudents = studentRepository.findByUsernameAndAgeGreaterThan("student2", 18);

        // then
        assertThat(findStudents.size()).isEqualTo(1);
        assertThat(findStudents.get(0).getUsername()).isEqualTo("student2");
        assertThat(findStudents.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void testQuery() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when
        List<Student> findStudents = studentRepository.findStudent("student2", 20);

        // then
        assertThat(findStudents.size()).isEqualTo(1);
        assertThat(findStudents.get(0).getUsername()).isEqualTo("student2");
        assertThat(findStudents.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void findUsernameList() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when
        List<String> usernameList = studentRepository.findUsernameList();

        // then
        assertThat(usernameList.size()).isEqualTo(2);
    }

    @Test
    void findStudentDTO() {
        // given
        Department department = new Department("Computer Science");
        departmentRepository.save(department);

        Student student1 = new Student("student1", 10, department);
        Student student2 = new Student("student2", 20, department);
        studentRepository.save(student1);
        studentRepository.save(student2);

        // when
        List<StudentDTO> list = studentRepository.findStudentDTO();

        // then
        StudentDTO studentDTO = list.get(0);
        assertThat(list.size()).isEqualTo(2);
        assertThat(studentDTO.getId()).isEqualTo(student1.getId());
        assertThat(studentDTO.getDepartmentName()).isEqualTo(department.getName());
        list.forEach(System.out::println);
    }

    @Test
    void findByNames() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        Student student3 = new Student("student3", 25);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        // when
        List<Student> list = studentRepository.findByNames(List.of("student1", "student2"));

        // then
        assertThat(list.size()).isEqualTo(2);
        list.forEach(System.out::println);
    }

    @Test
    void testReturnType_List() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        Student student3 = new Student("student3", 25);
        Student student4 = new Student("student3", 25);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        // when
        List<Student> list = studentRepository.findListByUsername("student1");
        // then
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void testReturnType_Entity() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        Student student3 = new Student("student3", 25);
        Student student4 = new Student("student3", 25);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        // when
        Student student = studentRepository.findStudentByUsername("student1");
        // then
        assertThat(student).isEqualTo(student1);
    }

    @Test
    void testReturnTypeOptional() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        Student student3 = new Student("student3", 25);
        Student student4 = new Student("student3", 25);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        // when
        Optional<Student> opt1 = studentRepository.findOptionalByUsername("student1");
        Optional<Student> opt2 = studentRepository.findOptionalByUsername("student4");
        // then
        assertThat(opt1.isPresent()).isEqualTo(true);
        assertThat(opt2.isEmpty()).isEqualTo(true);
    }

    @Test
    void testReturnTypeDuplicate() {
        // given
        Student student1 = new Student("student1", 10);
        Student student2 = new Student("student2", 20);
        Student student3 = new Student("student3", 25);
        Student student4 = new Student("student3", 25);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            studentRepository.findOptionalByUsername("student3");
        }).isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

}
