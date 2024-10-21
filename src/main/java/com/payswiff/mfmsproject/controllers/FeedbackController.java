package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.reuquests.CreateFeedbackRequest;
import com.payswiff.mfmsproject.reuquests.FeedbackRequestWrapper;
import com.payswiff.mfmsproject.services.FeedbackService;

import jakarta.persistence.Id;
import jakarta.validation.Valid;

import com.payswiff.mfmsproject.dtos.AverageRatingResponseDTO;
import com.payswiff.mfmsproject.dtos.DeviceFeedbackCountDTO;
import com.payswiff.mfmsproject.dtos.EmployeeFeedbackCountDto;
import com.payswiff.mfmsproject.dtos.FeedbackQuestionAnswerAssignDto;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173") // Allow specific origin

public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	/**
	 * Endpoint to create feedback.
	 *
	 * @param feedbackRequest The request object containing feedback data.
	 * @return ResponseEntity containing the created Feedback and HTTP status.
	 * @throws Exception 
	 */
//	@PostMapping("/create")
//	public ResponseEntity<HttpStatus> createFeedback(@Valid @RequestBody CreateFeedbackRequest feedbackRequest)
//	       throws ResourceNotFoundException, MerchantDeviceNotAssignedException {
//
//	    boolean isFeedbackCreated = (boolean) feedbackService.createFeedback(feedbackRequest);
//	    // Return 201 if feedback is created, else return 500 (internal server error)
//	    return isFeedbackCreated ? new ResponseEntity<>(HttpStatus.CREATED) 
//	                             : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createFeedback(
	        @Valid @RequestBody FeedbackRequestWrapper requestWrapper)
	        throws Exception {

	    CreateFeedbackRequest feedbackRequest = requestWrapper.getFeedbackRequest();
	    List<FeedbackQuestionAnswerAssignDto> questionAnswers = requestWrapper.getQuestionAnswers();

	    // Ensure that 10 questions are passed
	    if (questionAnswers.size() != 10) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return bad request if not exactly 10 questions
	    }

	    boolean isFeedbackCreated = feedbackService.createFeedback(feedbackRequest, questionAnswers);

	    return isFeedbackCreated ? new ResponseEntity<>(HttpStatus.CREATED)
	                             : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    
    @GetMapping("/allfeedbackscount")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<EmployeeFeedbackCountDto>> getAllFeedbacksCount() {
        List<EmployeeFeedbackCountDto> feedbackCounts = feedbackService.countFeedbacksForAllEmployees();
        return ResponseEntity.ok(feedbackCounts);
    }
    
    @GetMapping("/average-rating-by-device")
    public ResponseEntity<List<AverageRatingResponseDTO>> getAverageRatingByDevice() {
        List<AverageRatingResponseDTO> averageRatings = feedbackService.getAverageRatingByDevice();
        return ResponseEntity.ok(averageRatings);
    }
    
    @GetMapping("/device-count")
    public ResponseEntity<List<DeviceFeedbackCountDTO>> getFeedbackCountByDevice() {
        List<DeviceFeedbackCountDTO> feedbackCounts = feedbackService.getFeedbackCountByDevice();
        return ResponseEntity.ok(feedbackCounts);
    }
}