package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1")
public class StudentManagementController {
    private static final List<Student> STUDS = Arrays.asList(
            new Student(Long.valueOf(1), "kanda"),
            new Student(Long.valueOf(2), "guru"),
            new Student(Long.valueOf(3), "venkat")
    );

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('course:read','course:write') and hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents() {
        return STUDS;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('course:write')")
    public void register(@RequestBody Student s) {
        System.out.println(s);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('course:write')")
    public void deleteStudent(@PathVariable Long id) {
        System.out.println(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('course:write')")
    public void updateStudent(@PathVariable Long id, @RequestBody Student s) {
        System.out.println(String.format("%s %s", s, id));
    }
}
