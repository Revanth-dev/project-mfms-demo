package com.payswiff.mfmsproject.dtos;

public class ForgotPasswordDto {
	
	private String emailOrPhone;
	private String resetPassword;
	/**
	 * @return the emailOrPhone
	 */
	public String getEmailOrPhone() {
		return emailOrPhone;
	}
	/**
	 * @param emailOrPhone the emailOrPhone to set
	 */
	public void setEmailOrPhone(String emailOrPhone) {
		this.emailOrPhone = emailOrPhone;
	}
	/**
	 * @return the resetPassword
	 */
	public String getResetPassword() {
		return resetPassword;
	}
	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}
	/**
	 * @param emailOrPhone
	 * @param resetPassword
	 */
	public ForgotPasswordDto(String emailOrPhone, String resetPassword) {
		this.emailOrPhone = emailOrPhone;
		this.resetPassword = resetPassword;
	}
	
}
