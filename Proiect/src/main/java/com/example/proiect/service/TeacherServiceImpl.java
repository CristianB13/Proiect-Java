package com.example.proiect.service;

import com.example.proiect.model.Request;
import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import com.example.proiect.repository.RequestRepository;
import com.example.proiect.repository.StudentRepository;
import com.example.proiect.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService{
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final RequestRepository requestRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Teacher get(String username) {
        return teacherRepository.findTeacherByUsername(username).orElse(null);
    }

    @Override
    public Teacher add(Teacher teacher) {
        teacher.setId(null);
        if(teacherRepository.findTeacherByUsername(teacher.getUsername()).isPresent()) throw new IllegalArgumentException("Username already in use");
        if(Objects.isNull(teacher.getUsername()) || teacher.getUsername().isEmpty()) throw new IllegalArgumentException("Username required");
        if(Objects.isNull(teacher.getPassword()) || teacher.getPassword().isEmpty()) throw new IllegalArgumentException("Password required");
        if(Objects.isNull(teacher.getFirstName()) || teacher.getFirstName().isEmpty()) throw new IllegalArgumentException("First Name required");
        if(Objects.isNull(teacher.getLastName()) || teacher.getLastName().isEmpty()) throw new IllegalArgumentException("Last Name required");
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher update(Teacher teacher, String username) {
        Teacher updatedTeacher = teacherRepository.findTeacherByUsername(username).orElse(null);
        if(Objects.isNull(updatedTeacher)) throw new IllegalArgumentException("Teacher not found");
        if(!Objects.isNull(teacher.getFirstName())) {
            updatedTeacher.setFirstName(teacher.getFirstName());
        }
        if(!Objects.isNull(teacher.getLastName())) {
            updatedTeacher.setLastName(teacher.getLastName());
        }
        return teacherRepository.save(updatedTeacher);
    }

    @Override
    public void acceptRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(Objects.isNull(request)) throw new IllegalArgumentException("Request not found");
        request.setApproved(true);
    }

    @Override
    public void rejectRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(Objects.isNull(request)) throw new IllegalArgumentException("Request not found");
        request.setApproved(false);
    }

    @Override
    public void delete(String username) {
        teacherRepository.deleteTeacherByUsername(username);
    }

    @Override
    public List<Student> getStudents(String username) {
        Teacher teacher = teacherRepository.findTeacherByUsername(username).orElse(null);
        return studentRepository.findStudentsByTeacher(teacher);
    }

    @Override
    public List<Request> getRequests(String username) {
        Teacher teacher = teacherRepository.findTeacherByUsername(username).orElse(null);
        return requestRepository.findRequestsByTeacher(teacher);
    }
}
