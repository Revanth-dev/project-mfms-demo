package com.payswiff.mfmsproject.dtos;

public class EmailOtpResponseDto {
	
	  private boolean emailSent;
	  private String otp;
	  private int statusCode;
	/**
	 * @return the emailSent
	 */
	public boolean isEmailSent() {
		return emailSent;
	}
	/**
	 * @param emailSent the emailSent to set
	 */
	public void setEmailSent(boolean emailSent) {
		this.emailSent = emailSent;
	}
	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}
	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @param emailSent
	 * @param otp
	 * @param statusCode
	 */
	public EmailOtpResponseDto(boolean emailSent, String otp, int statusCode) {
		this.emailSent = emailSent;
		this.otp = otp;
		this.statusCode = statusCode;
	}
	/**
	 * 
	 */
	public EmailOtpResponseDto() {
	}
	  

}
