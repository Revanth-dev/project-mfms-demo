package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.services.FeedbackQuestionsAssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.payswiff.mfmsproject.dtos.FeedbackQuestionDTO;
import java.util.List;

/**
 * Controller for managing Feedback Questions Associations.
 */
@RestController
@RequestMapping("/api/FeedbackQuestions")
public class FeedbackQuestionsAssociationController {

    @Autowired
    private FeedbackQuestionsAssociationService feedbackQuestionsAssociationService;

    /**
     * Endpoint to retrieve feedback questions by feedback ID.
     *
     * @param feedbackId The ID of the feedback.
     * @return A ResponseEntity containing a list of FeedbackQuestionDTOs or an error message.
     */
    @GetMapping("/feedback/questions/{feedbackId}")
    public ResponseEntity<List<FeedbackQuestionDTO>> getFeedbackQuestionsByFeedbackId(@PathVariable Integer feedbackId) {
        try {
            List<FeedbackQuestionDTO> feedbackQuestions = feedbackQuestionsAssociationService.getFeedbackQuestionsByFeedbackId(feedbackId);
            return new ResponseEntity<>(feedbackQuestions, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
