package com.example.data_jpa_1.repository;

import com.example.data_jpa_1.dto.StudentDTO;
import com.example.data_jpa_1.entity.Student;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select s from Student s where s.username = :username and s.age = :age")
    List<Student> findStudent(@Param("username") String username, @Param("age") int age);

    @Query("select s.username from Student s")
    List<String> findUsernameList();

    @Query("select new com.example.data_jpa_1.dto.StudentDTO(s.id, s.username, d.name) from Student s join s.department d")
    List<StudentDTO> findStudentDTO();

    @Query("select s from Student s where s.username in :names")
    List<Student> findByNames(@Param("names") List<String> names);

    List<Student> findListByUsername(String username);

    Student findStudentByUsername(String username);

    Optional<Student> findOptionalByUsername(String username);

    @Modifying
    @Query("update Student s set s.age = s.age + 1 where s.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Modifying(clearAutomatically = true)
    @Query("update Student s set s.age = s.age + 1 where s.age >= :age")
    int bulkClearAgePlus(@Param("age") int age);

    @Override
    @EntityGraph(attributePaths = {"department"})
    List<Student> findAll();

    @EntityGraph(attributePaths = {"department"})
    @Query("select s from Student s")
    List<Student> findGraphAll();

    @EntityGraph(attributePaths = {"department"})
    @Query("select s from Student s where s.username = :username")
    List<Student> findGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Student> findReadOnlyById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Student> findLockByUsername(String username);

}
