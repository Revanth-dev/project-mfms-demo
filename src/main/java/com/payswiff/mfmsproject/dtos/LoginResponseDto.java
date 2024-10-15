package com.payswiff.mfmsproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDto {
	private String userEmailOrPhone;
    private String role;
    private String token;
	/**
	 * @return the userEmailOrPhone
	 */
	public String getUserEmailOrPhone() {
		return userEmailOrPhone;
	}
	/**
	 * @param userEmailOrPhone the userEmailOrPhone to set
	 */
	public void setUserEmailOrPhone(String userEmailOrPhone) {
		this.userEmailOrPhone = userEmailOrPhone;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param userEmailOrPhone
	 * @param role
	 * @param token
	 */
	public LoginResponseDto(String userEmailOrPhone, String role, String token) {
		this.userEmailOrPhone = userEmailOrPhone;
		this.role = role;
		this.token = token;
	}
    
}
