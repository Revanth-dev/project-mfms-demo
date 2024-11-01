package com.payswiff.mfmsproject.dtos;

/**
 * EmailResponseDto is a Data Transfer Object that encapsulates the response
 * details of an email sending operation.
 */
public class EmailResponseDto {
    
    private boolean emailSent; // Indicates if the email was successfully sent
    private int statusCode; // HTTP status code representing the result of the email operation

    /**
     * Constructs a new EmailResponseDto with specified fields.
     *
     * @param emailSent Indicates if the email was sent successfully.
     * @param statusCode The status code of the email sending operation.
     */
    public EmailResponseDto(boolean emailSent, int statusCode) {
        this.emailSent = emailSent;
        this.statusCode = statusCode;
    }

    /**
     * Default constructor for EmailResponseDto.
     */
    public EmailResponseDto() {
    }

    /**
     * Checks if the email was sent.
     *
     * @return true if the email was sent, false otherwise.
     */
    public boolean isEmailSent() {
        return emailSent;
    }

    /**
     * Gets the status code of the email sending operation.
     *
     * @return the status code as an integer.
     */
    public int getStatusCode() {
        return statusCode;
    }
}
