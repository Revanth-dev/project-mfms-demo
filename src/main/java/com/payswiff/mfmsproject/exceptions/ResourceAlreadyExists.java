/**
 * 
 */
package com.payswiff.mfmsproject.exceptions;

/**
 * 
 */
public class ResourceAlreadyExists extends Exception{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resource;  
    private String field;     
    private String value;     
    
    public ResourceAlreadyExists(String resource, String field, String value) {
        super(String.format("%s with %s: %s is exists.", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

}
