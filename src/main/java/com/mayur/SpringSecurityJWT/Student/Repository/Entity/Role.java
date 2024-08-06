package com.mayur.SpringSecurityJWT.Student.Repository.Entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@RequiredArgsConstructor
public enum Role {
    STUDENT,
    ADMIN;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
