package com.example.proiect.repository;

import com.example.proiect.model.Request;
import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findRequestsByTeacher(Teacher teacher);
    List<Request> findRequestsByStudent(Student student);
}
