package com.example.spring.CafeManagerApplication.controllers;

import com.example.spring.CafeManagerApplication.service.FileUpload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final AuthenticationManager authenticationManager;
    private final FileUpload fileUpload;
    public TestController(AuthenticationManager authenticationManager, FileUpload fileUpload) {
        this.authenticationManager = authenticationManager;
        this.fileUpload = fileUpload;
    }

    @GetMapping("teststring")
    public String getString(@AuthenticationPrincipal UserDetails userDetails){


        return "User Details: " + userDetails.getUsername();
    }

    @GetMapping("teststring1")
    public String getString1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return "User Details: " + userName;
    }

    @PostMapping("file")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return fileUpload.uploadFile(multipartFile);
    }


}
