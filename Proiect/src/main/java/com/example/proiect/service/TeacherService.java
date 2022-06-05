package com.example.proiect.service;

import com.example.proiect.model.Request;
import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher get(String username);
    Teacher add(Teacher teacher);
    Teacher update(Teacher teacher, String username);
    void delete(String username);
    List<Student> getStudents(String username);
    List<Request> getRequests(String username);
    void acceptRequest(Long requestId);
    void rejectRequest(Long requestId);
}
