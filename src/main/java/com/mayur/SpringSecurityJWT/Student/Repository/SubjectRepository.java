/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Student.Repository;

import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dell
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

   @Query("SELECT sub FROM Student s JOIN s.subjects sub WHERE s.id = :studentId")
    List<Subject> findSubjectsByStudentId(@Param("studentId") Integer studentId);

}
