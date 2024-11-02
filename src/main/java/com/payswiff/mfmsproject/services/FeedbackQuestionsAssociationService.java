package com.payswiff.mfmsproject.services;

import org.modelmapper.ModelMapper;
import com.payswiff.mfmsproject.dtos.FeedbackQuestionDTO;
import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.models.FeedbackQuestionsAssociation;
import com.payswiff.mfmsproject.models.Question;
import com.payswiff.mfmsproject.repositories.FeedbackQuestionsAssociationRepository;
import com.payswiff.mfmsproject.repositories.FeedbackRepository;
import com.payswiff.mfmsproject.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @throws ResourceUnableToCreate if the request object or its fields are invalid.
     */
    public FeedbackQuestionsAssociation createAssociation(FeedbackQuestionsAssociation request) 
            throws ResourceNotFoundException, ResourceUnableToCreate {

        // Validate that the request is not null
        if (request == null) {
            throw new ResourceUnableToCreate("FeedbackQuestionsAssociation", "Request object cannot be null","Null or Empty");
        }

        // Validate feedback existence
        if (request.getFeedback() == null || request.getFeedback().getFeedbackId() == null) {
            throw new ResourceUnableToCreate("FeedbackQuestionsAssociation", "Feedback and its ID cannot be null or empty","Null or Empty");
        }

        Feedback feedback = feedbackRepository.findById(request.getFeedback().getFeedbackId())
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "ID", String.valueOf(request.getFeedback().getFeedbackId())));

        // Validate question existence
        if (request.getQuestion() == null || request.getQuestion().getQuestionId() == null) {
            throw new ResourceUnableToCreate("FeedbackQuestionsAssociation", "Question and its ID cannot be null or empty","Null or Empty");
        }

        Question question = questionRepository.findById(request.getQuestion().getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "ID", String.valueOf(request.getQuestion().getQuestionId())));

        // Validate answer
        if (request.getAnswer() == null || request.getAnswer().trim().isEmpty()) {
            throw new ResourceUnableToCreate("FeedbackQuestionsAssociation", "Answer cannot be null or empty","Null or Empty");
        }

        // Create the association
        ModelMapper modelMapper = new ModelMapper();
        FeedbackQuestionsAssociation association = modelMapper.map(request, FeedbackQuestionsAssociation.class);
        association.setFeedback(feedback);  // Set the feedback object
        association.setQuestion(question);    // Set the question object
        association.setAnswer(request.getAnswer()); // Set the answer from the request
        
        try {
        	return associationRepository.save(association); // Save the association
        }catch (Exception e) {
			// TODO: handle exception
        	throw new ResourceUnableToCreate("FeedbackQuestionAssociation", "feedback and question", "internal error");
		}
    }

    /**
     * Retrieves a list of FeedbackQuestionDTOs by feedback ID.
     *
     * @param feedbackId The ID of the feedback for which to retrieve associations.
     * @return A list of FeedbackQuestionDTOs associated with the given feedback ID.
     * @throws ResourceNotFoundException if the feedback or its associations are not found.
     * @throws ResourceUnableToCreate if the feedback ID is invalid.
     */
    public List<FeedbackQuestionDTO> getFeedbackQuestionsByFeedbackId(Integer feedbackId) 
            throws ResourceNotFoundException, ResourceUnableToCreate {

        // Validate feedback ID
        if (feedbackId == null) {
            throw new ResourceUnableToCreate("FeedbackQuestionsAssociation", "Feedback ID cannot be null","Null");
        }

        // Retrieve feedback by ID
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "ID", feedbackId.toString()));

        // Fetch associations using the feedback object
        List<FeedbackQuestionsAssociation> associations = associationRepository.findByFeedback(feedback);

        if (associations.isEmpty()) {
            throw new ResourceNotFoundException("FeedbackQuestionsAssociation", "Feedback ID", feedbackId.toString());
        }

        // Map to DTOs
        return associations.stream()
                .map(association -> new FeedbackQuestionDTO(
                        association.getQuestion().getQuestionId(),
                        association.getQuestion().getQuestionDescription(),
                        association.getAnswer()))
                .collect(Collectors.toList());
    }
}
