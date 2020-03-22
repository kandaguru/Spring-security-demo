package com.example.demo.security;

import com.example.demo.exception.AuthEntryPoint;
import com.example.demo.jwt.AuthorizationFilter;
import com.example.demo.jwt.JWTUsernamePasswordAuthFilter;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private CustomUserDetailsService userDetailService;

    private PasswordEncoder passwordEncoder;

    private AuthEntryPoint auth;

    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService userDetailService, PasswordEncoder passwordEncoder, AuthEntryPoint auth, AccessDeniedHandler accessDeniedHandler) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.auth = auth;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().
                exceptionHandling().authenticationEntryPoint(auth).accessDeniedHandler(accessDeniedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/students/", "/api/v1/students/index").permitAll()
                .antMatchers("/api/v1/students/**").hasRole(AppRoles.STUDENT.name())
                .anyRequest()
                .authenticated()
                .and().addFilter(jwtUsernamePasswordAuthFilter())
                .addFilterAfter(authorizationFilter(), JWTUsernamePasswordAuthFilter.class);
//                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public JWTUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter() throws Exception {
        JWTUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter = new JWTUsernamePasswordAuthFilter();
        jwtUsernamePasswordAuthFilter.setFilterProcessesUrl("/api/users/authenticate");
        jwtUsernamePasswordAuthFilter.setAuthenticationManager(authenticationManager());
        return jwtUsernamePasswordAuthFilter;

    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter();
    }


}
