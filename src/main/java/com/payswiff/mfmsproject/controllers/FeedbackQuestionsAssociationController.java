package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
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
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.2.4:5173"})

public class FeedbackQuestionsAssociationController {

    @Autowired
    private FeedbackQuestionsAssociationService feedbackQuestionsAssociationService;
   

    /**
     * Endpoint to retrieve feedback questions by feedback ID.
     *
     * @param feedbackId The ID of the feedback.
     * @return A ResponseEntity containing a list of FeedbackQuestionDTOs or an error message.
     * @throws ResourceUnableToCreate if feedbackId is null.
     */
    @GetMapping("/feedback/questions/{feedbackId}")
    public ResponseEntity<List<FeedbackQuestionDTO>> getFeedbackQuestionsByFeedbackId(@PathVariable Integer feedbackId) throws ResourceUnableToCreate {
        if (feedbackId == null) {
            throw new ResourceUnableToCreate("Feedback ID cannot be null.","","");
        }

        try {
            List<FeedbackQuestionDTO> feedbackQuestions = feedbackQuestionsAssociationService.getFeedbackQuestionsByFeedbackId(feedbackId);
            return new ResponseEntity<>(feedbackQuestions, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) { // Catch unexpected runtime exceptions
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
