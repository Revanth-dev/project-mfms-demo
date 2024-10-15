package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.reuquests.CreateFeedbackRequest;
import com.payswiff.mfmsproject.services.FeedbackService;

import jakarta.validation.Valid;

import com.payswiff.mfmsproject.exceptions.MerchantDeviceNotAssignedException;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	/**
	 * Endpoint to create feedback.
	 *
	 * @param feedbackRequest The request object containing feedback data.
	 * @return ResponseEntity containing the created Feedback and HTTP status.
	 * @throws ResourceNotFoundException
	 * @throws MerchantDeviceNotAssignedException 
	 */
	@PostMapping("/create")
	public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody CreateFeedbackRequest feedbackRequest)
			throws ResourceNotFoundException, MerchantDeviceNotAssignedException {

		Feedback feedback = feedbackService.createFeedback(feedbackRequest);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.CREATED);
	}
	
	/**
     * Retrieves feedback based on provided filters.
     *
     * @param employeeId The ID of the employee (optional).
     * @param deviceId   The ID of the device (optional).
     * @param rating     The feedback rating (optional).
     * @param merchantId The ID of the merchant (optional).
     * @return ResponseEntity containing a list of feedback.
     * @throws ResourceNotFoundException if any of the provided IDs do not exist.
     */
    @GetMapping("/getallfeedbacks")
    public ResponseEntity<List<Feedback>> getFeedbacksByFilters(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long deviceId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Long merchantId) throws ResourceNotFoundException {
        
        List<Feedback> feedbacks = feedbackService.getFeedbacksByFilters(employeeId, deviceId, rating, merchantId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
}