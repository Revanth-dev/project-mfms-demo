package com.payswiff.mfmsproject.dtos;

public class DeviceFeedbackCountDTO {
	
	private Long deviceId;
    private Long feedbackCount; // Use Long for count
	/**
	 * @return the deviceId
	 */
	public Long getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return the feedbackCount
	 */
	public Long getFeedbackCount() {
		return feedbackCount;
	}
	/**
	 * @param feedbackCount the feedbackCount to set
	 */
	public void setFeedbackCount(Long feedbackCount) {
		this.feedbackCount = feedbackCount;
	}
	/**
	 * @param deviceId
	 * @param feedbackCount
	 */
	public DeviceFeedbackCountDTO(Long deviceId, Long feedbackCount) {
		this.deviceId = deviceId;
		this.feedbackCount = feedbackCount;
	}
	/**
	 * 
	 */
	public DeviceFeedbackCountDTO() {
	}
    
    
}
