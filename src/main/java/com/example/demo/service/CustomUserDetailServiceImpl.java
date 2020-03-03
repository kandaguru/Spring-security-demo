package com.example.demo.service;

import com.example.demo.dao.UserDetailServiceDao;
import com.example.demo.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServiceDao userDetailServiceDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userDetailServiceDao.getByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException(String.format("username %s not found", s));
        return user;

    }
}










        /*
          if (s.contains("kanda"))
            return User.builder().username("kanda").password(passwordEncoder.encode("pass"))
//                    .roles(AppRoles.ADMIN.name())
                    .authorities(AppRoles.ADMIN.getGrantedAuthorities())
                    .build();
        if (s.contains("Guru"))
            return User.builder().username("Guru").password(passwordEncoder.encode("pass"))
//                    .roles(AppRoles.ADMIN_TRAINEE.name())
                    .authorities(AppRoles.ADMIN_TRAINEE.getGrantedAuthorities())
                    .build();
        if (s.contains("venkat"))
            return User.builder().username("venkat").password(passwordEncoder.encode("pass"))
//                    .roles(AppRoles.STUDENT.name())
                    .authorities(AppRoles.STUDENT.getGrantedAuthorities())
                    .build();


        throw new UsernameNotFoundException("No such user");

         */
