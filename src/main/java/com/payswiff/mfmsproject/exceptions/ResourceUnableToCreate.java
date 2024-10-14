package com.payswiff.mfmsproject.exceptions;

public class ResourceUnableToCreate extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resource;
    private String field;    
    private String value; 
    
    public ResourceUnableToCreate(String resource, String field, String value) {
        // Construct a formatted error message using the provided resource, field, and value.
        super(String.format("%s with %s: %s is unable to create at this moment!!", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
