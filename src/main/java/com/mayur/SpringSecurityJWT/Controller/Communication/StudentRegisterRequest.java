/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Controller.Communication;

import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

/**
 *
 * @author dell
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Set<Integer> subjectIds;
    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
    private Role role;
}
