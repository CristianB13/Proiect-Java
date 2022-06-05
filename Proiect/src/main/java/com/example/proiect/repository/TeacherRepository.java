package com.example.proiect.repository;

import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("select t from Teacher t where t not in (select r.teacher from Request r join r.student where r.student = :student)")
    List<Teacher> findAvailableTeachers(Student student);

    Optional<Teacher> findTeacherByUsername(String username);
    void deleteTeacherByUsername(String username);
}
