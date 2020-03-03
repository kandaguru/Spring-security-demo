package com.example.demo.jwt;

import com.example.demo.security.User;
import com.example.demo.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTConfig jwtConfig;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException {

        try {
            String token = request.getHeader("Authorization");
            String jwt = token.replace("Bearer ", "");

            if (!(jwt.equals(null)) && jwtTokenProvider.ValidateToken(jwt)) {

                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes()))
                        .build().parseClaimsJws(jwt);

                String username = claims.getBody().getSubject();

                User user = (User) userDetailsService.loadUserByUsername(username);
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("issue with spring security context");
        }

        filterChain.doFilter(request, response);
    }
}
