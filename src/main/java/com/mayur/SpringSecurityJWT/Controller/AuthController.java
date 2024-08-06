/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayur.SpringSecurityJWT.Controller;

import com.mayur.SpringSecurityJWT.Exception.ErrorResponse;
import com.mayur.SpringSecurityJWT.service.StudentService;
import com.mayur.SpringSecurityJWT.Controller.Communication.StudentRegisterRequest;
import com.mayur.SpringSecurityJWT.service.SubjectService;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Student;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.StudentDetailsList;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import java.util.HashSet;
//import com.mayur.SpringSecurityJWT.user.Repositery.Entity.User;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author dell
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final StudentService studentService;
    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody StudentRegisterRequest registerRequest) {
        try {
            boolean isEmailIdTaken = studentService.checkEmailId(registerRequest.getEmail());

            if (isEmailIdTaken) {
                String message = "Student with Email " + registerRequest.getEmail() + " already registered";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message, HttpStatus.BAD_REQUEST.value()));
            } else {

                if (registerRequest.getRole() == null) {
                    registerRequest.setRole(Role.STUDENT);
                }
                Student student = studentService.createStudent(registerRequest);

                if (student != null) {
                    return ResponseEntity.ok(student);
                } else {
                    String message = "Failed to create student";
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
            }
        } catch (Exception e) {

            String message = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudentById(@PathVariable int id) {

        if (id == 1) {
            String message = "You cannot delete this student with id =" + id;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message, HttpStatus.NOT_FOUND.value()));
        }
        boolean deleted = studentService.deleteStudentById(id);

        if (deleted) {
            //  return ResponseEntity.ok("User with ID " + id + " deleted successfully");

            String message = "Student with ID " + id + " deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(message, HttpStatus.OK.value()));
        } else {
            String message = "Student with ID " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message, HttpStatus.NOT_FOUND.value()));

        }
    }

    @GetMapping("/students")
    public ResponseEntity<Object> getStudents() {
        try {
            List<Student> students = studentService.getAllStudents();

            for (Student student : students) {

                List<Subject> list = subjectService.getAllSubjectsbyId(student.getId());

                Set<Subject> subjectSet = new HashSet<>(list);

                student.setSubjects(subjectSet);
                System.out.println("list" + list.size());

            }

            //   List<Student> student = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching users. Please try again later.");
        }
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> getSubjects() {
        try {
            List<Subject> subjects = subjectService.getAllSubjects();
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching users. Please try again later.");
        }
    }

}
