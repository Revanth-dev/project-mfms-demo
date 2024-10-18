package com.payswiff.mfmsproject.dtos;

public class AverageRatingResponseDTO {
	private Long deviceId;
	private Double averageRating;
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
	 * @return the averageRating
	 */
	public Double getAverageRating() {
		return averageRating;
	}
	/**
	 * @param averageRating the averageRating to set
	 */
	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
	/**
	 * @param deviceId
	 * @param averageRating
	 */
	public AverageRatingResponseDTO(Long deviceId, Double averageRating) {
		this.deviceId = deviceId;
		this.averageRating = averageRating;
	}
	/**
	 * 
	 */
	public AverageRatingResponseDTO() {
	}
	
}
