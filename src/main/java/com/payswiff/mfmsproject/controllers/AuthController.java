package com.payswiff.mfmsproject.controllers; // Package declaration for the controllers

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired annotation for dependency injection
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for HTTP response handling
import org.springframework.web.bind.annotation.CrossOrigin; // Importing CrossOrigin annotation for CORS support
import org.springframework.web.bind.annotation.PostMapping; // Importing PostMapping for handling POST requests
import org.springframework.web.bind.annotation.RequestBody; // Importing RequestBody for reading request bodies
import org.springframework.web.bind.annotation.RequestMapping; // Importing RequestMapping for mapping web requests
import org.springframework.web.bind.annotation.RestController; // Importing RestController to indicate a RESTful controller

import com.payswiff.mfmsproject.dtos.ForgotPasswordDto; // Importing DTO for forgot password requests
import com.payswiff.mfmsproject.dtos.LoginDto; // Importing DTO for login requests
import com.payswiff.mfmsproject.dtos.LoginResponseDto; // Importing DTO for login response
import com.payswiff.mfmsproject.exceptions.EmployeePasswordUpdationFailedException; // Importing custom exception for password update failures
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing custom exception for resource not found scenarios
import com.payswiff.mfmsproject.services.AuthService; // Importing service for authentication operations
import com.payswiff.mfmsproject.services.EmployeeService; // Importing service for employee operations

/**
 * REST controller for handling authentication-related operations.
 * This includes login and password recovery functionalities.
 */
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/authentication") // Base URL for all authentication-related endpoints
@CrossOrigin(origins = "http://localhost:5173") // Allow CORS for specified origin (frontend URL)
public class AuthController {
    
    @Autowired // Automatically inject the AuthService bean
    private AuthService authService; // Service for authentication operations

    /**
     * Endpoint for user login.
     *
     * @param loginDto the login data transfer object containing username and password
     * @return ResponseEntity containing the login response
     */
    @PostMapping("/login") // Maps POST requests to /api/authentication/login
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        // Use the AuthService to perform login and retrieve the response
        LoginResponseDto response = authService.login(loginDto); // Authenticate user and get response
        return ResponseEntity.ok(response); // Return HTTP 200 OK with the response body
    }
    
    /**
     * Endpoint for password recovery.
     *
     * @param forgotPasswordDto the forgot password data transfer object containing email or username
     * @return boolean indicating success or failure of the operation
     * @throws ResourceNotFoundException if the user is not found
     * @throws EmployeePasswordUpdationFailedException if the password update fails
     */
    @PostMapping("/forgotpassword") // Maps POST requests to /api/authentication/forgotpassword
    public boolean forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws ResourceNotFoundException, EmployeePasswordUpdationFailedException {
        // Calls the AuthService to process the password recovery request
        return authService.forgotPassword(forgotPasswordDto); // Return the result of the operation
    }
}
