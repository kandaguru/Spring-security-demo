package com.example.demo.jwt;

import com.example.demo.security.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {
        AuthRequest authRequest;

        try {
            authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Authentication authObj = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return authObj;
    }


    /**
     * AuthenticationManager.authenticate()
     * will get the will use the "userDetailsService" to authenticate
     * and assigns the "authResult" object with the values of the authenticated user
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws
            IOException, ServletException {


        Map Claims = new HashMap<>();
        User user = (User) authResult.getPrincipal();
        System.err.println(authResult.getAuthorities());

        Claims.put("authorities", authResult.getAuthorities());
        Claims.put("username", user.getUsername());


        /**
         * order of the builder pattern matters,
         * setClaims().setSubject()
         */

        String token = Jwts.builder()
                .setClaims(Claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(jwtConfig.getExpiry())))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes()),
                        SignatureAlgorithm.HS512)
                .compact();

        response.getWriter().print(jwtConfig.getPrefix() + " " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws
            IOException, ServletException {

        response.setStatus(401);

    }
}
