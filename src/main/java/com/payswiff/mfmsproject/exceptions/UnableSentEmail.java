package com.payswiff.mfmsproject.exceptions;

public class UnableSentEmail extends Exception{

	private String email;

	/**
	 * @param email
	 */
	public UnableSentEmail(String email) {
        super(String.format("Email is unable to send to %s", email));
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
