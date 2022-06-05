package com.example.proiect.service;

import com.example.proiect.model.Request;
import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;

import java.util.List;

public interface StudentService {
    Student get(String username);
    Student add(Student student);
    Student update(Student student, String username);
    Student setTeacher(Student student, Long teacherId);
    void delete(String username);
    Request addRequest(String username, Long teacherId);
    List<Request> getRequests(String username);
    List<Teacher> getAvailableTeachers(String username);
}
