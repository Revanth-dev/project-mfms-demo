package com.payswiff.mfmsproject.dtos;

public class MerchantDeviceCountDTO {
	
	private Long merchantId;
    private Long deviceCount;
	/**
	 * @return the merchantId
	 */
	public Long getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the deviceCount
	 */
	public Long getDeviceCount() {
		return deviceCount;
	}
	/**
	 * @param deviceCount the deviceCount to set
	 */
	public void setDeviceCount(Long deviceCount) {
		this.deviceCount = deviceCount;
	}
	/**
	 * @param merchantId
	 * @param deviceCount
	 */
	public MerchantDeviceCountDTO(Long merchantId, Long deviceCount) {
		this.merchantId = merchantId;
		this.deviceCount = deviceCount;
	}
	
	
    
}
