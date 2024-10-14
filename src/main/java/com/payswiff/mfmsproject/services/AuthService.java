package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.dtos.LoginDto;
import com.payswiff.mfmsproject.dtos.LoginResponseDto;
import com.payswiff.mfmsproject.security.CustomeUserDetailsService;
import com.payswiff.mfmsproject.security.JwtAuthenticationEntryPoint;
import com.payswiff.mfmsproject.security.JwtAuthenticationFilter;
import com.payswiff.mfmsproject.security.JwtTokenProvider;

@Service
public class AuthService {
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomeUserDetailsService userDetailsService;
    
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto login(LoginDto loginDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmailOrPhone(), loginDto.getPassword())
        );
        
        // If authentication is successful, store the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
        String token =jwtTokenProvider.generateToken(authentication);

        // Retrieve user details (email and roles)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String userEmail = userDetails.getUsername();
        String role = userDetails.getAuthorities().stream()
                                 .findFirst() // Assuming a single role for simplicity
                                 .orElseThrow(() -> new RuntimeException("No roles assigned"))
                                 .getAuthority();

        // Return the response with email and role
        return new LoginResponseDto(userEmail, role,token);
    }
}
