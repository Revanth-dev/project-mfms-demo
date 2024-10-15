package com.payswiff.mfmsproject.services;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.dtos.ForgotPasswordDto;
import com.payswiff.mfmsproject.dtos.LoginDto;
import com.payswiff.mfmsproject.dtos.LoginResponseDto;
import com.payswiff.mfmsproject.exceptions.EmployeePasswordUpdationFailedException;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.repositories.EmployeeRepository;
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
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	EmployeeService employeeService;

	public LoginResponseDto login(LoginDto loginDto) {
		// Authenticate the user
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmailOrPhone(), loginDto.getPassword()));

		// If authentication is successful, store the authentication in the security
		// context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		// Retrieve user details (email and roles)
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String userEmail = userDetails.getUsername();
		String role = userDetails.getAuthorities().stream().findFirst() // Assuming a single role for simplicity
				.orElseThrow(() -> new RuntimeException("No roles assigned")).getAuthority();

		// Return the response with email and role
		return new LoginResponseDto(userEmail, role, token);
	}

	public boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) throws ResourceNotFoundException, EmployeePasswordUpdationFailedException {
		String emailOrPhone = forgotPasswordDto.getEmailOrPhone();

		// Regular expression for validating email
		String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
		// Updated regular expression for validating phone numbers (exactly 10 digits)
		String phoneRegex = "^[0-9]{10}$";

		Pattern emailPattern = Pattern.compile(emailRegex);
		Pattern phonePattern = Pattern.compile(phoneRegex);

		Matcher emailMatcher = emailPattern.matcher(emailOrPhone);
		Matcher phoneMatcher = phonePattern.matcher(emailOrPhone);

		boolean isEmail = emailMatcher.matches();
		boolean isPhone = phoneMatcher.matches();

		// Check if it's either an email or a phone number
		if (isEmail) {
			if (!employeeRepository.existsByEmployeeEmail(forgotPasswordDto.getEmailOrPhone())) {
				throw new ResourceNotFoundException("Employee", "Email", forgotPasswordDto.getEmailOrPhone());
			}
			if(!employeeService.updateEmployeePassword(forgotPasswordDto.getEmailOrPhone(),
					forgotPasswordDto.getResetPassword())) {
				throw new EmployeePasswordUpdationFailedException("Employee", "Email", emailOrPhone);
			}
			return true; // Email is valid
		} else if (isPhone) {
			if (!employeeRepository.existsByEmployeePhoneNumber(forgotPasswordDto.getEmailOrPhone())) {
				throw new ResourceNotFoundException("Employee", "Phone", forgotPasswordDto.getEmailOrPhone());
			}
			if(!employeeService.updateEmployeePassword(forgotPasswordDto.getEmailOrPhone(),
					forgotPasswordDto.getResetPassword())) {
				throw new EmployeePasswordUpdationFailedException("Employee", "Phone", emailOrPhone);
			}
			return true; // Phone number is validS
		} else {
			// Invalid email or phone number
			return false;
		}

	}
}
