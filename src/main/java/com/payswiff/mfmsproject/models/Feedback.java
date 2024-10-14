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


}
