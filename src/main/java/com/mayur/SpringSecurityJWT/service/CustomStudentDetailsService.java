/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.service;

import com.mayur.SpringSecurityJWT.Student.Repository.AdminRepository;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Student;
import com.mayur.SpringSecurityJWT.Student.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@Service
@RequiredArgsConstructor
public class CustomStudentDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;
    
    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails user = studentRepository.findByEmail(email).orElse(null);


        if (user == null) {
            user = adminRepository.findByEmail(email).orElse(null);
        }


        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
