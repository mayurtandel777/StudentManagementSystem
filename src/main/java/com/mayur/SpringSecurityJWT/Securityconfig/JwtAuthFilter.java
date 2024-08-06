package com.mayur.SpringSecurityJWT.Securityconfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.mayur.SpringSecurityJWT.Exception.JwtAuthenticationException;
import com.mayur.SpringSecurityJWT.service.CustomStudentDetailsService;
import com.mayur.SpringSecurityJWT.service.StudentService;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    // private final StudentService studentService;
    private final CustomStudentDetailsService studentDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
//        try {
//            // Verify whether request has Authorization header and it has Bearer in it
//            final String authHeader = request.getHeader("Authorization");
//            final String jwt;
//            final String email;
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            // Extract JWT from the Authorization header
//            jwt = authHeader.substring(7);
//            email = jwtService.extractUsername(jwt);

        // Verify whether token is valid
//            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
//  
//                // If valid, set to security context holder
//                if (jwtService.isTokenValid(jwt, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//                    authToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                } else {
//                    // Set appropriate status and message
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.getWriter().write("Invalid JWT token");
//                    return;
//                }
//            }
//        } catch (JwtAuthenticationException e) {
//            // Set appropriate status and message
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("JWT Authentication error: " + e.getMessage());
//            return;
//        } catch (Exception e) {
//            // Set appropriate status and message
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("An unexpected error occurred: " + e.getMessage());
//            return;
//        }
//        try {
//            // Log request details
//            logger.info("Processing request: " + request.getRequestURI());
//
//            // Your filter logic here
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            // Log exception details
//            logger.error("Error processing request: " + e.getMessage(), e);
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: " + e.getMessage());
//        }
//    }
        try {

            // Verify whether request has Authorization header and it has Bearer in it
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String email;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                handleAuthenticationFailure(response, "Authorization header is missing or invalid. Expected format: Bearer <token>");
                return;
            }

            // Extract JWT from the Authorization header
            jwt = authHeader.substring(7);
            email = jwtService.extractUsername(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = studentDetailsService.loadUserByUsername(email);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    handleAuthenticationFailure(response, "Invalid JWT token");
                    return;
                }
            }
        } catch (JwtAuthenticationException e) {
            handleAuthenticationFailure(response, "JWT Authentication error: " + e.getMessage());
            return;
        } catch (Exception e) {
            handleAuthenticationFailure(response, "An unexpected error occurred: " + e.getMessage());
            return;
        }

        // Check for access to protected URLs
//        if (!hasAdminRole()) {
//            handleAccessDenied(response, "You do not have permission to access this resource");
//            return;
//        }
        // Check if the user has the required role for the request
        if (!hasRequiredAccess(request)) {
            handleAccessDenied(response, "You do not have permission to access this resource");
            return;
        }

        logger.info("Processing request: " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private boolean hasRequiredAccess(HttpServletRequest request) {
        // Get the roles of the currently authenticated user
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // Determine if the user is an admin
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Get the request path
        String path = request.getServletPath();

        // Define permissions for different endpoints
        if (isAdmin) {
            // Admins can access all endpoints
            return true;
        } else {
            // Users can only access specific endpoints
            if (path.equals("/auth/subjects")) {
                return true; // Users can access the endpoint to get all users
            }
        }

        return false; // Deny access if no role matches the required permissions
    }
//
//    logger.info ("Processing request: " + request.getRequestURI());
//    filterChain.doFilter (request, response);

//    private boolean hasAdminRole() {
//        // Check if the authenticated user has the admin role
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//    }
//     private boolean hasRoleNull() {
//        // Check if the authenticated user has the admin role
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase("NULL"));
//    }
    private void handleAuthenticationFailure(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        logger.error(message);
    }

    private void handleAccessDenied(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        logger.error(message);
    }

    //   filterChain.doFilter(request, response);
//    @Override
//    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
//        return request.getServletPath().contains("/api/Register");
//    }
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.contains("/api/Register") || path.contains("/api/login") || path.contains("api/AllSubjects");
    }

}
