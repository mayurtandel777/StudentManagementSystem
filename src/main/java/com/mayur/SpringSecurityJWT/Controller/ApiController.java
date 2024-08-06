package com.mayur.SpringSecurityJWT.Controller;

import com.mayur.SpringSecurityJWT.Controller.Communication.AuthenticationResponse;
import com.mayur.SpringSecurityJWT.Controller.Communication.AuthenticationRequest;
import com.mayur.SpringSecurityJWT.Controller.Communication.StudentRegisterRequest;
import com.mayur.SpringSecurityJWT.Exception.ErrorResponse;
//import com.mayur.SpringSecurityJWT.service.UserService;
import com.mayur.SpringSecurityJWT.Exception.InvalidCredentialsException;
import com.mayur.SpringSecurityJWT.service.StudentService;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Role;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Student;
import com.mayur.SpringSecurityJWT.Student.Repository.Entity.Subject;
import com.mayur.SpringSecurityJWT.service.SubjectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/Register")
    public ResponseEntity<Object> createStudent(@RequestBody StudentRegisterRequest studentRegisterRequest) {

        try {
            boolean isEmailIdTaken = studentService.checkEmailId(studentRegisterRequest.getEmail());

            if (isEmailIdTaken) {
                String message = "Student with Email " + studentRegisterRequest.getEmail() + " already registered";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message, HttpStatus.BAD_REQUEST.value()));
            } else {

                if (studentRegisterRequest.getRole() == null) {
                    studentRegisterRequest.setRole(Role.STUDENT);
                }
                Student user = studentService.createStudent(studentRegisterRequest);

                if (user != null) {
                    return ResponseEntity.ok(user);
                } else {
                    String message = "Failed to create Student";
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
            }
        } catch (Exception e) {

            String message = "An unexpected error occurred: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = studentService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Invalid credentials"));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthenticationResponse("An unexpected error occurred: " + e.getMessage()));
        }

    }

    @GetMapping("/AllSubjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

}
