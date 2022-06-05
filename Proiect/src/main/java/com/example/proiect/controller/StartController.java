package com.example.proiect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class StartController {

    @GetMapping()
    public String getStartPage(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(e -> e.toString()).collect(Collectors.toList());
        if(authorities.contains("TEACHER"))
            return "teacherProfile";
        else if(authorities.contains("STUDENT"))
            return "studentProfile";
        else return "login";
    }

    @GetMapping("public/{name}")
    public String getPublicFile(@PathVariable("name") String file) {
        return "public/"+file;
    }

    @ResponseBody
    @GetMapping(value = "images/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPhoto(@PathVariable("name") String image) {
        var img = new ClassPathResource("static/images/"+image);
        try {
            byte[] bytes = StreamUtils.copyToByteArray(img.getInputStream());
            return ResponseEntity.ok().body(bytes);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "register";
        }
        return "redirect:/";
    }
}
