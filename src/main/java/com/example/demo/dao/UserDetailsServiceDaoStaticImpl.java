package com.example.demo.dao;

import com.example.demo.security.AppRoles;
import com.example.demo.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserDetailsServiceDaoStaticImpl implements UserDetailServiceDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        return generateStaticUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    private List<User> generateStaticUsers() {
        return Arrays.asList(
                new User(AppRoles.ADMIN.getGrantedAuthorities(),
                        "kanda", passwordEncoder.encode("pass"), true, true, true, true),
                new User(AppRoles.ADMIN_TRAINEE.getGrantedAuthorities(),
                        "guru", passwordEncoder.encode("pass"), true, true, true, true),
                new User(AppRoles.STUDENT.getGrantedAuthorities(),
                        "venkat", passwordEncoder.encode("pass"), true, true, true, true)
        );
    }
}
