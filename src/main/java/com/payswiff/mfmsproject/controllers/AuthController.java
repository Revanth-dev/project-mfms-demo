package com.payswiff.mfmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payswiff.mfmsproject.dtos.LoginDto;
import com.payswiff.mfmsproject.dtos.LoginResponseDto;
import com.payswiff.mfmsproject.services.AuthService;

@RestController
@RequestMapping("/api/authentication/")
public class AuthController {
	
	@Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        // Use the AuthService to perform login and retrieve the response
        LoginResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    
    
	
}
