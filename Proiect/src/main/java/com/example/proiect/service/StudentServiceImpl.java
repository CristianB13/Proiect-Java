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
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final RequestRepository requestRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Student get(String username) {
        return studentRepository.findStudentByUsername(username).orElse(null);
    }

    @Override
    public Student add(Student student) {
        student.setId(null);
        if(studentRepository.findStudentByUsername(student.getUsername()).isPresent()) throw new IllegalArgumentException("Username already in use");
        if(Objects.isNull(student.getUsername()) || student.getUsername().isEmpty()) throw new IllegalArgumentException("Username required");
        if(Objects.isNull(student.getPassword()) || student.getPassword().isEmpty()) throw new IllegalArgumentException("Password required");
        if(Objects.isNull(student.getFirstName()) || student.getFirstName().isEmpty()) throw new IllegalArgumentException("First Name required");
        if(Objects.isNull(student.getLastName()) || student.getLastName().isEmpty()) throw new IllegalArgumentException("Last Name required");
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student, String username) {
        Student updatedStudent = studentRepository.findStudentByUsername(username).orElse(null);
        if(Objects.isNull(updatedStudent)) throw new IllegalArgumentException("Student not found");
        if(!Objects.isNull(student.getFirstName())) {
            updatedStudent.setFirstName(student.getLastName());
        }
        if(!Objects.isNull(student.getLastName())) {
            updatedStudent.setLastName(student.getLastName());
        }
        return studentRepository.save(updatedStudent);
    }

    @Override
    public Student setTeacher(Student student, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(Objects.isNull(teacher)) throw new IllegalArgumentException("Teacher not found");
        student.setTeacher(teacher);
        return studentRepository.save(student);
    }

    @Override
    public void delete(String username) {
        studentRepository.deleteStudentByUsername(username);
    }

    @Override
    public Request addRequest(String username, Long teacherId) {
        Student student = studentRepository.findStudentByUsername(username).orElse(null);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return requestRepository.save(new Request(null, student, teacher, null));
    }

    @Override
    public List<Request> getRequests(String username) {
        Student student = studentRepository.findStudentByUsername(username).orElse(null);
        return requestRepository.findRequestsByStudent(student);
    }

    @Override
    public List<Teacher> getAvailableTeachers(String username) {
        Student student = studentRepository.findStudentByUsername(username).orElse(null);
        return teacherRepository.findAvailableTeachers(student);
    }
}
