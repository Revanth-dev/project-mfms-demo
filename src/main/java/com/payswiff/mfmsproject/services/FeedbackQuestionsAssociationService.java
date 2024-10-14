package com.payswiff.mfmsproject.services;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.models.FeedbackQuestionsAssociation;
import com.payswiff.mfmsproject.models.Question;
import com.payswiff.mfmsproject.repositories.FeedbackQuestionsAssociationRepository;
import com.payswiff.mfmsproject.repositories.FeedbackRepository;
import com.payswiff.mfmsproject.repositories.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to handle operations related to Feedback-Question Associations.
 */
@Service
public class FeedbackQuestionsAssociationService {

    private final FeedbackQuestionsAssociationRepository associationRepository;
    private final FeedbackRepository feedbackRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public FeedbackQuestionsAssociationService(
            FeedbackQuestionsAssociationRepository associationRepository,
            FeedbackRepository feedbackRepository,
            QuestionRepository questionRepository) {
        this.associationRepository = associationRepository;
        this.feedbackRepository = feedbackRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Creates a new FeedbackQuestionsAssociation.
     *
     * @param request The request object containing necessary data.
     * @return The created FeedbackQuestionsAssociation.
     * @throws ResourceNotFoundException if the feedback or question does not exist.
     * @throws ResourceAlreadyExists if the question has already been answered for the given feedback.
     */
    public FeedbackQuestionsAssociation createAssociation(FeedbackQuestionsAssociation request) throws  ResourceNotFoundException {
        
        // Validate feedback existence
        Feedback feedback = feedbackRepository.findById(request.getFeedback().getFeedbackId())
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "ID",String.valueOf(request.getFeedback().getFeedbackId())));

        // Validate question existence
        Question question = questionRepository.findById(request.getQuestion().getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "ID", String.valueOf(request.getQuestion().getQuestionId())));

        
        // Create the association
        FeedbackQuestionsAssociation association = FeedbackQuestionsAssociation.builder()
                .feedback(feedback)
                .question(question)
                .answer(request.getAnswer())
                .build();

        return associationRepository.save(association);
    }
}
