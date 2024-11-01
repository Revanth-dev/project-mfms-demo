package com.payswiff.mfmsproject.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender; // Spring's mail sender for sending emails
    
    private static final String OTP_CHARACTERS = "0123456789"; // Characters used for generating OTP
    private static final int OTP_LENGTH = 6; // Set the desired length for the OTP
    private SecureRandom random = new SecureRandom(); // SecureRandom for generating random numbers

    /**
     * Sends an email with the specified subject and text to the given recipient.
     *
     * @param to the recipient's email address
     * @param subject the subject of the email
     * @param text the body of the email
     * @return true if the email is sent successfully
     */
    public boolean sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); // Create a simple email message
        message.setTo(to); // Set the recipient
        message.setSubject(subject); // Set the subject line
        message.setText(text); // Set the body text of the email
        emailSender.send(message); // Send the email using JavaMailSender
        return true; // Return true to indicate the email was sent
    }
    
    /**
     * Generates a random OTP (One-Time Password) of specified length.
     *
     * @return a randomly generated OTP as a string
     */
    public String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH); // StringBuilder for building the OTP
        for (int i = 0; i < OTP_LENGTH; i++) {
            // Append a random character from OTP_CHARACTERS to the OTP
            otp.append(OTP_CHARACTERS.charAt(random.nextInt(OTP_CHARACTERS.length())));
        }
        return otp.toString(); // Return the generated OTP as a string
    }
}
