package com.payswiff.mfmsproject.controllers; // Package declaration for the EmailController

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired for dependency injection
import org.springframework.http.HttpStatus; // Importing HttpStatus for defining HTTP response statuses
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for handling HTTP responses
import org.springframework.web.bind.annotation.*; // Importing Spring web annotations

import com.payswiff.mfmsproject.dtos.EmailOtpResponseDto; // Importing DTO for OTP response
import com.payswiff.mfmsproject.dtos.EmailResponseDto; // Importing DTO for email response
import com.payswiff.mfmsproject.dtos.EmailSendDto; // Importing DTO for sending email data
import com.payswiff.mfmsproject.services.EmailService; // Importing EmailService to handle email logic

/**
 * REST controller for handling email-related operations such as sending emails
 * and generating One-Time Passwords (OTPs).
 */
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/email") // Base URL for all email-related APIs
@CrossOrigin(origins = "http://localhost:5173") // Allow CORS for specified origin (frontend URL)
public class EmailController {

    @Autowired // Automatically inject the EmailService bean
    private EmailService emailService; // Service for handling email-related logic

    /**
     * Sends an email using the provided details.
     *
     * @param emailSendDto The data transfer object containing email details (recipient, subject, text).
     * @return ResponseEntity containing the result of the email sending operation.
     */
    @PostMapping("/send-email") // Endpoint to send an email
    public ResponseEntity<EmailResponseDto> sendEmail(@RequestBody EmailSendDto emailSendDto) {
        // Call the EmailService to send the email and capture the success status
        boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());

        // Create response DTO with email sending status
        EmailResponseDto response = new EmailResponseDto(emailSent, emailSent ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Return response entity with appropriate HTTP status
        return new ResponseEntity<>(response, emailSent ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Generates an OTP and sends it to the specified email address.
     *
     * @param email The email address where the OTP will be sent.
     * @return ResponseEntity containing the OTP and the result of the sending operation.
     */
    @PostMapping("/generateOTP") // Endpoint to generate and send OTP via email
    public ResponseEntity<EmailOtpResponseDto> generateOtp(@RequestParam String email) {
        // Generate a One-Time Password (OTP)
        String otp = emailService.generateOtp();
        
        // Prepare the email content for sending the OTP
        EmailSendDto emailSendDto = new EmailSendDto();
        emailSendDto.setTo(email);
        emailSendDto.setSubject("Merchant Feedback Management System");
        emailSendDto.setText("Your One Time Password for login is: " + otp);
        
        // Send the OTP email and capture the success status
        boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());

        // Create response DTO with email sending status and the generated OTP
        EmailOtpResponseDto response = new EmailOtpResponseDto(emailSent, otp, emailSent ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Return response entity with appropriate HTTP status
        return new ResponseEntity<>(response, emailSent ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
