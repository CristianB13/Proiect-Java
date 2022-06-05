package com.example.proiect.controller;

import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import com.example.proiect.service.StudentService;
import com.example.proiect.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/register")
public class RegisterController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/student")
    public ResponseEntity registerStudent(@RequestBody Student student) {
        Student newStudent;
        try {
            newStudent = studentService.add(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student").toString());
        return ResponseEntity.created(uri).body(newStudent);
    }

    @PostMapping("/teacher")
    public ResponseEntity saveTeacher(@RequestBody Teacher teacher) {
        Teacher newTeacher;
        try {
            newTeacher = teacherService.add(teacher);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teacher").toString());
        return ResponseEntity.created(uri).body(newTeacher);
    }
}
