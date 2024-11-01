package com.payswiff.mfmsproject.exceptions;

/**
 * Exception thrown when a merchant device is not assigned.
 * This exception captures details about the resources involved
 * in the assignment failure.
 */
public class MerchantDeviceNotAssignedException extends Exception {

    private String resourceOne;  // The first resource involved in the exception
    private String fieldOne;     // The field of the first resource
    private String valueOne;     // The value of the first resource's field
    private String resourceTwo;  // The second resource involved in the exception
    private String fieldTwo;     // The field of the second resource
    private String valueTwo;     // The value of the second resource's field

    /**
     * Constructs a MerchantDeviceNotAssignedException with detailed information.
     *
     * @param resourceOne The name of the first resource (e.g., "Merchant").
     * @param fieldOne    The field of the first resource that is relevant (e.g., "ID").
     * @param valueOne    The value of the first resource's field that is involved.
     * @param resourceTwo  The name of the second resource (e.g., "Device").
     * @param fieldTwo    The field of the second resource that is relevant (e.g., "ID").
     * @param valueTwo    The value of the second resource's field that is involved.
     */
    public MerchantDeviceNotAssignedException(String resourceOne, String fieldOne, String valueOne,
                                              String resourceTwo, String fieldTwo, String valueTwo) {
        super(String.format("%s with %s: %s is not assigned to %s with %s: %s.", 
            resourceOne, fieldOne, valueOne, resourceTwo, fieldTwo, valueTwo));
        
        this.resourceOne = resourceOne;
        this.fieldOne = fieldOne;
        this.valueOne = valueOne;
        this.resourceTwo = resourceTwo;
        this.fieldTwo = fieldTwo;
        this.valueTwo = valueTwo;
    }

    // Getters can be added here if needed for the fields, for example:
    /**
     * Gets the name of the first resource.
     * 
     * @return The name of the first resource.
     */
    public String getResourceOne() {
        return resourceOne;
    }

    /**
     * Gets the field of the first resource.
     * 
     * @return The field of the first resource.
     */
    public String getFieldOne() {
        return fieldOne;
    }

    /**
     * Gets the value of the first resource's field.
     * 
     * @return The value of the first resource's field.
     */
    public String getValueOne() {
        return valueOne;
    }

    /**
     * Gets the name of the second resource.
     * 
     * @return The name of the second resource.
     */
    public String getResourceTwo() {
        return resourceTwo;
    }

    /**
     * Gets the field of the second resource.
     * 
     * @return The field of the second resource.
     */
    public String getFieldTwo() {
        return fieldTwo;
    }

    /**
     * Gets the value of the second resource's field.
     * 
     * @return The value of the second resource's field.
     */
    public String getValueTwo() {
        return valueTwo;
    }
}
