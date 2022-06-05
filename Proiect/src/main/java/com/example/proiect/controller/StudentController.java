package com.example.proiect.controller;

import com.example.proiect.model.Request;
import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import com.example.proiect.service.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping()
    public ResponseEntity getStudent(Authentication authentication) {
        Student student = studentService.get(authentication.getName());
        if(Objects.isNull(student)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(student);
    }

    @PutMapping()
    public ResponseEntity updateStudent(@RequestBody Student student, Authentication authentication) {
        Student updatedStudent;
        URI uri;
        try {
            if(Objects.isNull(studentService.get(authentication.getName()))) {
                uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student").toString());
                updatedStudent = studentService.add(student);
                return ResponseEntity.created(uri).body(updatedStudent);
            } else {
                updatedStudent = studentService.update(student, authentication.getName());
                return ResponseEntity.ok().body(updatedStudent);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity deleteStudent(Authentication authentication) {
        try {
            studentService.delete(authentication.getName());
            return ResponseEntity.ok().body("Successful delete");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Student not found");
        }
    }

    @GetMapping("/requests")
    public ResponseEntity getRequests(Authentication authentication) {
        return ResponseEntity.ok().body(studentService.getRequests(authentication.getName()));
    }

    @PostMapping("/request/{id}")
    public ResponseEntity addRequest(@PathVariable("id") Long id, Authentication authentication) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/request/"+id).toString());
            Request request = studentService.addRequest(authentication.getName(), id);
            return ResponseEntity.created(uri).body(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teacher")
    public ResponseEntity getMyTeacher(Authentication authentication) {
        Teacher teacher = studentService.get(authentication.getName()).getTeacher();
        if(Objects.isNull(teacher)) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok().body(teacher);
    }

    @PutMapping("/teacher/{id}")
    public ResponseEntity setMyTeacher(@PathVariable("id") Long id, Authentication authentication) {
        try {
            return ResponseEntity.ok().body(studentService.setTeacher(studentService.get(authentication.getName()), id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teachers")
    public ResponseEntity getAvailableTeachers(Authentication authentication) {
        return ResponseEntity.ok().body(studentService.getAvailableTeachers(authentication.getName()));
    }
}
