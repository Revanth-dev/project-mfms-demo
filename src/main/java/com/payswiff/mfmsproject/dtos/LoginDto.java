package com.payswiff.mfmsproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginDto {
	
	private String emailOrPhone;
	private String password;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param emailOrPhone
	 * @param password
	 */
	public LoginDto(String emailOrPhone, String password) {
		this.emailOrPhone = emailOrPhone;
		this.password = password;
	}
	
}
