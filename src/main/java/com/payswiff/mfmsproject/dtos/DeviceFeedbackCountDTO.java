package com.payswiff.mfmsproject.dtos;

/**
 * DeviceFeedbackCountDTO is a Data Transfer Object that encapsulates 
 * the count of feedback associated with a specific device.
 */
public class DeviceFeedbackCountDTO {

    private Long deviceId; // The unique identifier for the device
    private Long feedbackCount; // The total count of feedback associated with the device

    /**
     * Constructs a new DeviceFeedbackCountDTO with specified device ID and feedback count.
     * 
     * @param deviceId The unique identifier of the device.
     * @param feedbackCount The total count of feedback for the device.
     */
    public DeviceFeedbackCountDTO(Long deviceId, Long feedbackCount) {
        this.deviceId = deviceId;
        this.feedbackCount = feedbackCount;
    }

    /**
     * Default constructor for DeviceFeedbackCountDTO.
     */
    public DeviceFeedbackCountDTO() {
    }

    /**
     * Gets the device ID.
     * 
     * @return the deviceId
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the device ID.
     * 
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets the feedback count.
     * 
     * @return the feedbackCount
     */
    public Long getFeedbackCount() {
        return feedbackCount;
    }

    /**
     * Sets the feedback count.
     * 
     * @param feedbackCount the feedbackCount to set
     */
    public void setFeedbackCount(Long feedbackCount) {
        this.feedbackCount = feedbackCount;
    }
}
