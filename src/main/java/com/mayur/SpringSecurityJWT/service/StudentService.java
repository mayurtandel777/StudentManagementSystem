package com.mayur.SpringSecurityJWT.service;

import com.mayur.SpringSecurityJWT.Controller.Communication.AuthenticationRequest;
import com.mayur.SpringSecurityJWT.Controller.Communication.AuthenticationResponse;
import com.mayur.SpringSecurityJWT.Controller.Communication.StudentRegisterRequest;
import com.mayur.SpringSecurityJWT.Exception.InvalidCredentialsException;
import com.mayur.SpringSecurityJWT.Securityconfig.JwtService;
import com.mayur.SpringSecurityJWT.Student.Repository.AdminRepository;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Student;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.StudentDetailsList;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import com.mayur.SpringSecurityJWT.Student.Repository.StudentRepository;
import com.mayur.SpringSecurityJWT.Student.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SubjectRepository subjectRepository;

    public boolean checkEmailId(String Email) {

        return studentRepository.existsByEmail(Email);

    }

    public Student createStudent(StudentRegisterRequest registerRequest) {

        var user = Student.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .address(registerRequest.getAddress())
                .role(registerRequest.getRole())
                .build();

        Set<Integer> subjects = registerRequest.getSubjectIds();
        Set<Subject> subject = new HashSet<>();
        for (Integer subjectId : subjects) {
            Subject Getsubject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            subject.add(Getsubject);
        }
        user.setSubjects(subject);

        var savedUser = studentRepository.save(user);
        return savedUser;

    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails user = studentRepository.findByEmail(request.getEmail()).orElse(null);

            if (user == null) {
                user = adminRepository.findByEmail(request.getEmail()).orElse(null);
            }
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + request.getEmail());
            }

            String jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();

        } catch (BadCredentialsException e) {

            throw new InvalidCredentialsException("Incorrect username or password");
        } catch (RuntimeException e) {

            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    public boolean deleteStudentById(int id) {

        try {
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
