package com.payswiff.mfmsproject.dtos;

import org.springframework.stereotype.Service;

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
public class JwtAuthResponseDto {
	
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * @param accessToken
	 * @param tokenType
	 */
	public JwtAuthResponseDto(String accessToken, String tokenType) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	private String accessToken;
	
	private String tokenType="Bearer";
}	
