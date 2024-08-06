package com.mayur.SpringSecurityJWT.Securityconfig;

import com.mayur.SpringSecurityJWT.Student.Repository.AdminRepository;
import com.mayur.SpringSecurityJWT.Student.Repository.StudentRepository;
import com.mayur.SpringSecurityJWT.service.CustomStudentDetailsService;
import java.util.Optional;
//import com.mayur.SpringSecurityJWT.user.Repositery.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    //  private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    private final AdminRepository adminRepository;
    
    @Autowired
   private final CustomStudentDetailsService customStudentDetailsService;  

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username
//                -> studentRepository.findByEmail(username)
//                        .or(() -> adminRepository.findByEmail(username))
//                        // Return the user if found, otherwise throw an exception
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
//
//        //  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customStudentDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
