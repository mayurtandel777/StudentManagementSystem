/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Student.Repository;

import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Student;
import java.util.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dell
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByEmail(String Email);

    Optional<Student> findByEmail(String email);
     
//@Query(value = "SELECT s.id AS student_id, " +
//                   "s.first_name AS student_first_name, " +
//                   "s.last_name AS student_last_name, " +
//                   "s.email AS student_email, " +
//                   "s.password AS student_password, " +
//                   "s.address AS student_address, " +
//                   "s.role AS student_role, " +
//                   "subj.id AS subject_id, " +
//                   "subj.name AS subject_name " +
//                   "FROM student_table s " +
//                   "LEFT JOIN student_subject ss ON s.id = ss.student_id " +
//                   "LEFT JOIN subject_table subj ON ss.subject_id = subj.id",
//           nativeQuery = true)
//    List<Student> findAllWithSubjects();

}
