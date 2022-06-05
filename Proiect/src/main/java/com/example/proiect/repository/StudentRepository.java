package com.example.proiect.repository;

import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByUsername(String username);
    List<Student> findStudentsByTeacher(Teacher teacher);
    void deleteStudentByUsername(String username);
}
