package com.payswiff.mfmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payswiff.mfmsproject.dtos.ForgotPasswordDto;
import com.payswiff.mfmsproject.dtos.LoginDto;
import com.payswiff.mfmsproject.dtos.LoginResponseDto;
import com.payswiff.mfmsproject.exceptions.EmployeePasswordUpdationFailedException;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.services.AuthService;
import com.payswiff.mfmsproject.services.EmployeeService;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {
	
	@Autowired
    private AuthService authService;
	
	

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        // Use the AuthService to perform login and retrieve the response
        LoginResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/forgotpassword")
    public boolean forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) throws ResourceNotFoundException, EmployeePasswordUpdationFailedException {
    	
    	return authService.forgotPassword(forgotPasswordDto);
		
    	
    }
	
}
