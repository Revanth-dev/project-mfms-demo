package com.payswiff.mfmsproject.exceptions;

public class MerchantDeviceNotAssignedException extends Exception{
	
	private String resourceOne;  
    private String fieldOne;     
    private String valueOne;  
    private String resourceTwo;  
    private String fieldTwo;     
    private String valueTwo;
	/**
	 * @param resourceOne
	 * @param fieldOne
	 * @param valueOne
	 * @param resourceTwo
	 * @param fieldTwo
	 * @param valueTwo
	 */
	public MerchantDeviceNotAssignedException(String resourceOne, String fieldOne, String valueOne, String resourceTwo,
			String fieldTwo, String valueTwo) {
		super(String.format("%s with %s: %s is not assigned to %s with %s: %s.", resourceOne, fieldOne, valueOne,
				resourceTwo,fieldTwo,valueTwo));
		
		this.resourceOne = resourceOne;
		this.fieldOne = fieldOne;
		this.valueOne = valueOne;
		this.resourceTwo = resourceTwo;
		this.fieldTwo = fieldTwo;
		this.valueTwo = valueTwo;
	}
	
    

}
