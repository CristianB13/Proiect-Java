package com.example.proiect.controller;

import com.example.proiect.model.Teacher;
import com.example.proiect.service.TeacherService;
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
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping()
    public ResponseEntity getTeacher(Authentication authentication) {
        Teacher teacher = teacherService.get(authentication.getName());
        if(Objects.isNull(teacher)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(teacher);
    }

    @PutMapping()
    public ResponseEntity updateTeacher(@RequestBody Teacher teacher, Authentication authentication) {
        Teacher updatedTeacher;
        URI uri;
        try {
            if(Objects.isNull(teacherService.get(authentication.getName()))) {
                uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teacher").toString());
                updatedTeacher = teacherService.add(teacher);
                return ResponseEntity.created(uri).body(updatedTeacher);
            } else {
                updatedTeacher = teacherService.update(teacher, authentication.getName());
                return ResponseEntity.ok().body(updatedTeacher);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity deleteTeacher(Authentication authentication) {
        try {
            teacherService.delete(authentication.getName());
            return ResponseEntity.ok().body("Successful delete");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Student not found");
        }
    }

    @GetMapping("students")
    public ResponseEntity getStudents(Authentication authentication) {
        return ResponseEntity.ok().body(teacherService.getStudents(authentication.getName()));
    }

    @PutMapping("/request/{id}")
    public ResponseEntity acceptRequest(@PathVariable("id") Long id) {
        try {
            teacherService.acceptRequest(id);
            return ResponseEntity.ok().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Request not found");
        }
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity rejectRequest(@PathVariable("id") Long id) {
        try {
            teacherService.rejectRequest(id);
            return ResponseEntity.ok().body(null);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("Request not found");
        }
    }

    @GetMapping("/requests")
    public ResponseEntity getRequests(Authentication authentication) {
        return ResponseEntity.ok().body(teacherService.getRequests(authentication.getName()));
    }
}
