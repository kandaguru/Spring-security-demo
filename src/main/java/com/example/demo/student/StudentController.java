package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDS = Arrays.asList(
            new Student(Long.valueOf(1), "kanda"),
            new Student(Long.valueOf(2), "guru"),
            new Student(Long.valueOf(3), "venkat")
    );


    @GetMapping({"/","/index"})
    public String Greeting(){
        return "hi";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public Student getStudent(@PathVariable Long id) {
        return STUDS.stream().filter((Student s) -> s.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Bad user Id"));
    }

}
