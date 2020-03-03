package com.example.demo.dao;

import com.example.demo.security.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailServiceDao {

    User getByUsername(String username);
}
