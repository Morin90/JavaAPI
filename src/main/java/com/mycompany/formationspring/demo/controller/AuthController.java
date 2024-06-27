package com.mycompany.formationspring.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    @GetMapping("/admin")
    public String admin() {
        return "Hello, Admin!";
    }
    @GetMapping("/user")
    public String helloUser() {
        return "Hello fucking User!";
    }
}
