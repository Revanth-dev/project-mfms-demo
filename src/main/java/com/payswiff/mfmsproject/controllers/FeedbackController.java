package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.reuquests.CreateFeedbackRequest;
import com.payswiff.mfmsproject.services.FeedbackService;

import jakarta.validation.Valid;

import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
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
	 */
	@PostMapping("/create")
	public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody CreateFeedbackRequest feedbackRequest)
			throws ResourceNotFoundException {

		Feedback feedback = feedbackService.createFeedback(feedbackRequest);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.CREATED);
	}
}