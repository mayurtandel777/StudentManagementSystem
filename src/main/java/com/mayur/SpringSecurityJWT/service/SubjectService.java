/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.service;

import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import com.mayur.SpringSecurityJWT.Student.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@RequiredArgsConstructor
@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Subject> getAllSubjectsbyId(Integer id) {
        return subjectRepository.findSubjectsByStudentId(id);
    }

}
