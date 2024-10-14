package com.payswiff.mfmsproject.reuquests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.payswiff.mfmsproject.models.Employee;

/**
 * Represents a request to create feedback.
 * This class captures all the necessary information required to create feedback.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFeedbackRequest {

  

    /**
     * The ID of the employee providing the feedback.
     */
    private Long feedbackEmployeeId; // ID of the employee providing the feedback

    /**
     * The ID of the merchant associated with the feedback.
     */
    private Long feedbackMerchantId; // ID of the merchant related to the feedback

    /**
     * The ID of the device associated with the feedback.
     */
    private Long feedbackDeviceId; // ID of the device related to the feedback

    /**
     * The URL or path for the first feedback image.
     */
    private String feedbackImage1; // URL or path for image 1

    /**
     * The rating given in the feedback.
     */
    private Integer feedbackRating; // Rating for the feedback

    /**
     * The actual feedback text provided by the user.
     */
    private String feedback; // Additional feedback text

    
}
