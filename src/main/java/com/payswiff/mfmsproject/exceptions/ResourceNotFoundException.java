package com.payswiff.mfmsproject.exceptions;

/**
 * Custom exception class that represents a "Resource Not Found" exception.
 * This exception is thrown when a requested resource (e.g., a Device or Merchant) 
 * is not found in the database.
 */
public class ResourceNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;  // Serial version UID for serialization compatibility.

    private String resource;  // Name of the resource that is not found (e.g., "Device", "Merchant").
    private String field;     // Name of the field that was used to search for the resource (e.g., "ID", "Model").
    private String value;     // Value of the field that was searched for (e.g., "MPOS", "123").

    /**
     * Constructor for ResourceNotFoundException.
     * 
     * @param resource The resource type (e.g., "Device", "Merchant") that could not be found.
     * @param field The field (e.g., "ID", "Model") that was used to search for the resource.
     * @param value The value of the field that was searched for (e.g., "MPOS").
     */
    public ResourceNotFoundException(String resource, String field, String value) {
        // Construct a formatted error message using the provided resource, field, and value.
        super(String.format("%s with %s: %s is not found!!", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

}
