package com.example.demo.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.security.AppPermissions.*;

public enum AppRoles {
    STUDENT(new HashSet<>()),
    ADMIN(new HashSet<>(Arrays.asList(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE))),
    ADMIN_TRAINEE(new HashSet<>(Arrays.asList(COURSE_READ, STUDENT_READ)));

    private Set<AppPermissions> permissions;

    AppRoles(Set<AppPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<AppPermissions> getPermissions() {
        return permissions;
    }

    /**
     * method to retrieve set of permissions/authorities based on a specific role : STUDENT,ADMIN,ADMIN_TRAINEE
     *
     * @return permessions based on the role
     */
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
            Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
            //Simple granted authority expects role to have ROLE_' '
            permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
            return permissions;
    }

}
