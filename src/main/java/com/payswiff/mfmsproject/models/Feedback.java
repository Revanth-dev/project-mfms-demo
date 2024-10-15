package com.payswiff.mfmsproject.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "feedback")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer feedbackId;  // Auto-increment ID for feedback

    @Column(name = "feedback_uuid", nullable = false, unique = true, length = 36)
    private String feedbackUuid;  // Unique UUID for feedback

    @ManyToOne
    @JoinColumn(name = "feedback_employee_id", nullable = false)
    private Employee feedbackEmployee;  // Foreign key referencing the employee

    @ManyToOne
    @JoinColumn(name = "feedback_merchant_id", nullable = false)
    private Merchant feedbackMerchant;  // Foreign key referencing the merchant

    @ManyToOne
    @JoinColumn(name = "feedback_device_id", nullable = false)
    private Device feedbackDevice;  // Foreign key referencing the device

    @Column(name = "feedback_image_1", nullable = false, length = 1000)
    private String feedbackImage1;  // URL or path for image 1
    @CreationTimestamp
    @Column(name = "feedback_creation_time", nullable = false)
    private LocalDateTime feedbackCreationTime;  // Creation timestamp
    @UpdateTimestamp
    @Column(name = "feedback_updation_time", nullable = false)
    private LocalDateTime feedbackUpdationTime;  // Update timestamp

    @Column(name = "feedback_rating", nullable = false)
    private Integer feedbackRating;  // Rating for the feedback

    @Column(name = "feedback", nullable = false)
    private String feedback;  // Additional feedback text

	/**
	 * @param feedbackId
	 * @param feedbackUuid
	 * @param feedbackEmployee
	 * @param feedbackMerchant
	 * @param feedbackDevice
	 * @param feedbackImage1
	 * @param feedbackCreationTime
	 * @param feedbackUpdationTime
	 * @param feedbackRating
	 * @param feedback
	 */
	public Feedback(Integer feedbackId, String feedbackUuid, Employee feedbackEmployee, Merchant feedbackMerchant,
			Device feedbackDevice, String feedbackImage1, LocalDateTime feedbackCreationTime,
			LocalDateTime feedbackUpdationTime, Integer feedbackRating, String feedback) {
		this.feedbackId = feedbackId;
		this.feedbackUuid = feedbackUuid;
		this.feedbackEmployee = feedbackEmployee;
		this.feedbackMerchant = feedbackMerchant;
		this.feedbackDevice = feedbackDevice;
		this.feedbackImage1 = feedbackImage1;
		this.feedbackCreationTime = feedbackCreationTime;
		this.feedbackUpdationTime = feedbackUpdationTime;
		this.feedbackRating = feedbackRating;
		this.feedback = feedback;
	}

	/**
	 * @return the feedbackId
	 */
	public Integer getFeedbackId() {
		return feedbackId;
	}

	/**
	 * @param feedbackId the feedbackId to set
	 */
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	/**
	 * @return the feedbackUuid
	 */
	public String getFeedbackUuid() {
		return feedbackUuid;
	}

	/**
	 * @param feedbackUuid the feedbackUuid to set
	 */
	public void setFeedbackUuid(String feedbackUuid) {
		this.feedbackUuid = feedbackUuid;
	}

	/**
	 * @return the feedbackEmployee
	 */
	public Employee getFeedbackEmployee() {
		return feedbackEmployee;
	}

	/**
	 * @param feedbackEmployee the feedbackEmployee to set
	 */
	public void setFeedbackEmployee(Employee feedbackEmployee) {
		this.feedbackEmployee = feedbackEmployee;
	}

	/**
	 * @return the feedbackMerchant
	 */
	public Merchant getFeedbackMerchant() {
		return feedbackMerchant;
	}

	/**
	 * @param feedbackMerchant the feedbackMerchant to set
	 */
	public void setFeedbackMerchant(Merchant feedbackMerchant) {
		this.feedbackMerchant = feedbackMerchant;
	}

	/**
	 * @return the feedbackDevice
	 */
	public Device getFeedbackDevice() {
		return feedbackDevice;
	}

	/**
	 * @param feedbackDevice the feedbackDevice to set
	 */
	public void setFeedbackDevice(Device feedbackDevice) {
		this.feedbackDevice = feedbackDevice;
	}

	/**
	 * @return the feedbackImage1
	 */
	public String getFeedbackImage1() {
		return feedbackImage1;
	}

	/**
	 * @param feedbackImage1 the feedbackImage1 to set
	 */
	public void setFeedbackImage1(String feedbackImage1) {
		this.feedbackImage1 = feedbackImage1;
	}

	/**
	 * @return the feedbackCreationTime
	 */
	public LocalDateTime getFeedbackCreationTime() {
		return feedbackCreationTime;
	}

	/**
	 * @param feedbackCreationTime the feedbackCreationTime to set
	 */
	public void setFeedbackCreationTime(LocalDateTime feedbackCreationTime) {
		this.feedbackCreationTime = feedbackCreationTime;
	}

	/**
	 * @return the feedbackUpdationTime
	 */
	public LocalDateTime getFeedbackUpdationTime() {
		return feedbackUpdationTime;
	}

	/**
	 * @param feedbackUpdationTime the feedbackUpdationTime to set
	 */
	public void setFeedbackUpdationTime(LocalDateTime feedbackUpdationTime) {
		this.feedbackUpdationTime = feedbackUpdationTime;
	}

	/**
	 * @return the feedbackRating
	 */
	public Integer getFeedbackRating() {
		return feedbackRating;
	}

	/**
	 * @param feedbackRating the feedbackRating to set
	 */
	public void setFeedbackRating(Integer feedbackRating) {
		this.feedbackRating = feedbackRating;
	}

	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * 
	 */
	public Feedback() {
	}
    
    

}
