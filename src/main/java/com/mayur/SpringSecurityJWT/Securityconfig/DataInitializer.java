/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Securityconfig;

import com.mayur.SpringSecurityJWT.Student.Repository.AdminRepository;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Admin;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import com.mayur.SpringSecurityJWT.Student.Repository.SubjectRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author dell
 */
@Configuration
public class DataInitializer {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            
            if (adminRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("password123"));
                admin.setAddress("MUMBAI");
                admin.setRole(Role.ADMIN);

                adminRepository.save(admin);
            }

            if (subjectRepository.count() == 0) {
                List<Subject> subjects = Arrays.asList(
                        createSubject(1, "Marathi"),
                        createSubject(2, "Hindi"),
                        createSubject(3, "English"),
                        createSubject(4, "Mathematics"),
                        createSubject(5, "Science"),
                        createSubject(6, "History")
                );
                subjectRepository.saveAll(subjects);
            }

        };
    }

    private Subject createSubject(Integer id, String name) {
        Subject subject = new Subject();
        subject.setId(id); 
        subject.setName(name);
        return subject;
    }
}
