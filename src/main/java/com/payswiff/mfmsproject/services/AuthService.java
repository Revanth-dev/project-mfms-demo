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
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
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

    /**
     * Authenticates a user based on the provided login credentials and generates a JWT token.
     *
     * @param loginDto contains login credentials (email or phone and password)
     * @return LoginResponseDto containing user details and JWT token
     * @throws ResourceUnableToCreate if the email/phone or password is null or empty
     */
    public LoginResponseDto login(LoginDto loginDto) throws ResourceUnableToCreate {
        // Validate that login credentials are not null or empty
        if (loginDto == null || loginDto.getEmailOrPhone() == null || loginDto.getEmailOrPhone().isEmpty()
                || loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            throw new ResourceUnableToCreate("Email/Phone or Password", "Login", "Login details are incomplete");
        }

        // Authenticate the user using the provided credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmailOrPhone(), loginDto.getPassword()));

        // Store the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JWT token for the authenticated user
        String token = jwtTokenProvider.generateToken(authentication);

        // Retrieve user details (email and roles) from the authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        // Get the first role assigned to the user
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No roles assigned"))
                .getAuthority();

        // Retrieve employee by email
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeEmail(userEmail);

        // Ensure the employee exists; throw exception if not found
        Employee employee = employeeOptional.orElseThrow(() -> new RuntimeException("Employee not found"));

        // Return the response containing the user's email, role, employee ID, and token
        return new LoginResponseDto(userEmail, role, employee.getEmployeeId(), token);
    }

    /**
     * Handles the password reset process for users who forgot their password.
     *
     * @param forgotPasswordDto contains the email or phone number and the new password
     * @return true if the password update was successful, false if the input is invalid
     * @throws ResourceNotFoundException if the employee with the given email or phone does not exist
     * @throws EmployeePasswordUpdationFailedException if updating the password fails
     * @throws ResourceUnableToCreate if input fields are null or empty
     */
    public boolean forgotPassword(ForgotPasswordDto forgotPasswordDto)
            throws ResourceNotFoundException, EmployeePasswordUpdationFailedException, ResourceUnableToCreate {

        // Validate that forgotPasswordDto and required fields are not null or empty
        if (forgotPasswordDto == null || forgotPasswordDto.getEmailOrPhone() == null 
                || forgotPasswordDto.getEmailOrPhone().isEmpty()
                || forgotPasswordDto.getResetPassword() == null || forgotPasswordDto.getResetPassword().isEmpty()) {
            throw new ResourceUnableToCreate("Email/Phone or Password", "ForgotPassword", "Details are incomplete");
        }

        String emailOrPhone = forgotPasswordDto.getEmailOrPhone();

        // Regular expression for validating email
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        // Regular expression for validating phone numbers (exactly 10 digits)
        String phoneRegex = "^[0-9]{10}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern phonePattern = Pattern.compile(phoneRegex);

        Matcher emailMatcher = emailPattern.matcher(emailOrPhone);
        Matcher phoneMatcher = phonePattern.matcher(emailOrPhone);

        // Check if the input is a valid email or phone number
        boolean isEmail = emailMatcher.matches();
        boolean isPhone = phoneMatcher.matches();

        // If the input is an email
        if (isEmail) {
            // Check if the employee exists by email
            if (!employeeRepository.existsByEmployeeEmail(forgotPasswordDto.getEmailOrPhone())) {
                throw new ResourceNotFoundException("Employee", "Email", forgotPasswordDto.getEmailOrPhone());
            }
            // Attempt to update the employee's password
            if (!employeeService.updateEmployeePassword(forgotPasswordDto.getEmailOrPhone(),
                    forgotPasswordDto.getResetPassword())) {
                throw new EmployeePasswordUpdationFailedException("Employee", "Email", emailOrPhone);
            }
            return true; // Password update successful
        } 
        // If the input is a phone number
        else if (isPhone) {
            // Check if the employee exists by phone number
            if (!employeeRepository.existsByEmployeePhoneNumber(forgotPasswordDto.getEmailOrPhone())) {
                throw new ResourceNotFoundException("Employee", "Phone", forgotPasswordDto.getEmailOrPhone());
            }
            // Attempt to update the employee's password
            if (!employeeService.updateEmployeePassword(forgotPasswordDto.getEmailOrPhone(),
                    forgotPasswordDto.getResetPassword())) {
                throw new EmployeePasswordUpdationFailedException("Employee", "Phone", emailOrPhone);
            }
            return true; // Password update successful
        } else {
            // Return false if neither a valid email nor phone number is provided
            return false; 
        }
    }
}
