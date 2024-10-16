package com.payswiff.mfmsproject.dtos;


public class EmailResponseDto {
    private boolean emailSent;
    private int statusCode;

    public boolean isEmailSent() {
        return emailSent;
    }

    public int getStatusCode() {
        return statusCode;
    }

	/**
	 * @param emailSent
	 * @param statusCode
	 */
	public EmailResponseDto(boolean emailSent, int statusCode) {
		this.emailSent = emailSent;
		this.statusCode = statusCode;
	}

	/**
	 * 
	 */
	public EmailResponseDto() {
	}
    
}
