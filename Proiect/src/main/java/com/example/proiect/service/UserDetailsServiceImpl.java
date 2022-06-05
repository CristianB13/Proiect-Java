package com.example.proiect.service;

import com.example.proiect.model.Student;
import com.example.proiect.model.Teacher;
import com.example.proiect.repository.StudentRepository;
import com.example.proiect.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int lastIndex = username.lastIndexOf(':');
        String name = username.substring(0, lastIndex);
        String role = username.substring(lastIndex+1);
        User.UserBuilder userBuilder;
        switch (role) {
            case "TEACHER" : {
                Teacher teacher = teacherRepository.findTeacherByUsername(name).orElse(null);
                if(Objects.isNull(teacher)) throw new UsernameNotFoundException("Teacher not found");
                userBuilder = User.withUsername(teacher.getUsername());
                userBuilder.password(teacher.getPassword());
                userBuilder.authorities(new String[]{"TEACHER"});
                break;
            }
            case "STUDENT" : {
                Student student = studentRepository.findStudentByUsername(name).orElse(null);
                if(Objects.isNull(student)) throw new UsernameNotFoundException("Student not found");
                userBuilder = User.withUsername(student.getUsername());
                userBuilder.password(student.getPassword());
                userBuilder.authorities(new String[]{"STUDENT"});
                break;
            }
            default : throw new UsernameNotFoundException("User not found");
        }
        return userBuilder.build();
    }
}
