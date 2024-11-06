package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.models.Feedback; // Importing the Feedback model for handling feedback data.
import com.payswiff.mfmsproject.reuquests.CreateFeedbackRequest; // Importing the request object for creating feedback.
import com.payswiff.mfmsproject.reuquests.FeedbackRequestWrapper; // Importing the wrapper for feedback requests.
import com.payswiff.mfmsproject.services.FeedbackService; // Importing the service layer for business logic related to feedback.

import jakarta.validation.Valid; // Importing annotation for validating request bodies.
import com.payswiff.mfmsproject.dtos.AverageRatingResponseDTO; // Importing DTO for average rating responses.
import com.payswiff.mfmsproject.dtos.DeviceFeedbackCountDTO; // Importing DTO for device feedback count.
import com.payswiff.mfmsproject.dtos.EmployeeFeedbackCountDto; // Importing DTO for employee feedback count.
import com.payswiff.mfmsproject.dtos.FeedbackQuestionAnswerAssignDto; // Importing DTO for question-answer pairs in feedback.
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing exception for handling resource not found errors.
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;

import java.util.List; // Importing List for handling collections of feedback and DTOs.

import org.springframework.beans.factory.annotation.Autowired; // Importing annotation for dependency injection.
import org.springframework.http.HttpStatus; // Importing HTTP status codes for responses.
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for building HTTP responses.
import org.springframework.web.bind.annotation.*; // Importing Spring MVC annotations for RESTful web services.

/**
 * FeedbackController handles requests related to feedback management,
 * including creating feedback and retrieving feedback based on various filters.
 */
@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from this origin
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService; // Injecting the FeedbackService for business logic

    /**
     * Creates feedback based on the provided request wrapper.
     *
     * @param requestWrapper The wrapper containing feedback request and question answers.
     * @return ResponseEntity with HTTP status indicating the result of the creation process.
     * @throws Exception If an error occurs during feedback creation.
     */
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createFeedback(
            @Valid @RequestBody FeedbackRequestWrapper requestWrapper) throws Exception {
    	//check null
    	if(requestWrapper==null){
    		throw new ResourceUnableToCreate("Feedback can not be created with null request", null, null);
    	}

        // Extracting the feedback request and question answers from the wrapper
        CreateFeedbackRequest feedbackRequest = requestWrapper.getFeedbackRequest();
        List<FeedbackQuestionAnswerAssignDto> questionAnswers = requestWrapper.getQuestionAnswers();

        // Ensure that exactly 10 questions are provided in the feedback
        if (questionAnswers.size() != 10) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return bad request if not exactly 10 questions
        }

        // Attempt to create the feedback and capture the result
        boolean isFeedbackCreated = feedbackService.createFeedback(feedbackRequest, questionAnswers);

        // Return appropriate HTTP status based on the creation result
        return isFeedbackCreated ? new ResponseEntity<>(HttpStatus.CREATED)
                                 : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieves feedback based on provided filters such as employee ID, device ID, rating, and merchant ID.
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
    	
//    	// Check if all parameters are null or empty
//        if (employeeId == null && deviceId == null && rating == null && merchantId == null) {
//            throw new ResourceNotFoundException("At least one filter parameter (employeeId, deviceId, rating, or merchantId) must be provided.", null, null);
//        }
//        
        // Fetch feedbacks based on the provided filters from the service
        List<Feedback> feedbacks = feedbackService.getFeedbacksByFilters(employeeId, deviceId, rating, merchantId);
        
        // Return the feedbacks along with HTTP OK status
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
    
    /**
     * Retrieves the count of feedbacks for all employees.
     *
     * @return ResponseEntity containing a list of feedback counts for each employee.
     */
    @GetMapping("/allfeedbackscount")
    public ResponseEntity<List<EmployeeFeedbackCountDto>> getAllFeedbacksCount() {
        // Get feedback counts for all employees from the service
        List<EmployeeFeedbackCountDto> feedbackCounts = feedbackService.countFeedbacksForAllEmployees();
        
        // Return the feedback counts with HTTP OK status
        return ResponseEntity.ok(feedbackCounts);
    }
    
    /**
     * Retrieves the average rating based on device feedback.
     *
     * @return ResponseEntity containing a list of average ratings by device.
     */
    @GetMapping("/average-rating-by-device")
    public ResponseEntity<List<AverageRatingResponseDTO>> getAverageRatingByDevice() {
        // Get average ratings for devices from the service
        List<AverageRatingResponseDTO> averageRatings = feedbackService.getAverageRatingByDevice();
        
        // Return the average ratings with HTTP OK status
        return ResponseEntity.ok(averageRatings);
    }
    
    /**
     * Retrieves feedback count grouped by device.
     *
     * @return ResponseEntity containing a list of feedback counts categorized by device.
     */
    @GetMapping("/device-count")
    public ResponseEntity<List<DeviceFeedbackCountDTO>> getFeedbackCountByDevice() {
        // Get feedback counts for devices from the service
        List<DeviceFeedbackCountDTO> feedbackCounts = feedbackService.getFeedbackCountByDevice();
        
        // Return the feedback counts with HTTP OK status
        return ResponseEntity.ok(feedbackCounts);
    }
}
