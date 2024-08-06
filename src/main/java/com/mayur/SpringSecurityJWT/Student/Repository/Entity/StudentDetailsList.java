/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Student.Repository.Entity;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author dell
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsList {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Subject> subjects = new HashSet<>();

}
