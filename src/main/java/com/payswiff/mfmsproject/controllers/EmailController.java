package com.payswiff.mfmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payswiff.mfmsproject.dtos.EmailOtpResponseDto;
import com.payswiff.mfmsproject.dtos.EmailResponseDto;
import com.payswiff.mfmsproject.dtos.EmailSendDto;
import com.payswiff.mfmsproject.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @PostMapping("/send-email")
    public ResponseEntity<EmailResponseDto> sendEmail(@RequestBody EmailSendDto emailSendDto) {
        boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());

        EmailResponseDto response = new EmailResponseDto(emailSent, emailSent ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, emailSent ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
 // Endpoint to generate OTP
    @PostMapping("/generateOTP")
    public ResponseEntity<EmailOtpResponseDto> generateOtp(@RequestParam String email) {
        String otp = emailService.generateOtp();
        // Here you can add logic to send the OTP via email
        // For example: emailService.sendOtp(email, otp);
        EmailSendDto emailSendDto = new EmailSendDto();
        emailSendDto.setTo(email);
        emailSendDto.setSubject("Merchant Feedback Management System");
        emailSendDto.setText("You One Time Password for login is: "+otp);
        
        boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());

        EmailOtpResponseDto response = new EmailOtpResponseDto(emailSent,otp, emailSent ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, emailSent ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
       
    }
}