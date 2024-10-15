/**
 * 
 */
package com.payswiff.mfmsproject.exceptions;

/**
 * 
 */
public class EmployeePasswordUpdationFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String resource;  
    private String emailOrPhone;     
    private String value;
	/**
	 * @param resource
	 * @param emailOrPhone
	 * @param value
	 */
	public EmployeePasswordUpdationFailedException(String resource, String emailOrPhone, String value) {
        super(String.format("%s with %s: %s is unable to change or update password at this moment..", resource, emailOrPhone, value));
		this.resource = resource;
		this.emailOrPhone = emailOrPhone;
		this.value = value;
	}
	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}  
    

}
