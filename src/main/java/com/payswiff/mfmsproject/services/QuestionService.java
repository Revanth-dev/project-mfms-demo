package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;  // Assuming you have this exception
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Question;
import com.payswiff.mfmsproject.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

   
    /**
     * Saves a question to the repository.
     *
     * @param question The question object to be saved.
     * @return The saved question object.
     * @throws ResourceAlreadyExists If a question with the same description already exists.
     * @throws ResourceUnableToCreate If the question description is empty or if an error occurs during saving.
     */
    public Question saveQuestion(Question question) throws ResourceAlreadyExists, ResourceUnableToCreate {
        // Validate the question description
        if (question.getQuestionDescription() == null || question.getQuestionDescription().trim().isEmpty()) {
            throw new ResourceUnableToCreate("Question", "Description cannot be empty","Empty Description");
        }
        if(question.getQuestionDescription().length()==0) {
            throw new ResourceUnableToCreate("Question", "Description cannot be empty","Empty Description");
        }

        // Check for existing question with the same description
        Optional<Question> existingQuestion = questionRepository.findByDescription(question.getQuestionDescription());

        if (existingQuestion.isPresent()) {
            throw new ResourceAlreadyExists("Question", "Description", question.getQuestionDescription());
        }

        try {
            // Attempt to save the question and return the saved question
            return questionRepository.save(question);
        } catch (Exception e) {
            // If any error occurs during saving, throw ResourceUnableToCreate
            throw new ResourceUnableToCreate("Question", "An error occurred while saving the question: " , e.getMessage());
        }
    }


    /**
     * Retrieves a Question by its ID.
     * 
     * @param id The ID of the question to retrieve.
     * @return The found Question object.
     * @throws ResourceNotFoundException If no question with the specified ID is found.
     */
    public Question getQuestionById(Long id) throws ResourceNotFoundException {
        return questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question", "ID", String.valueOf(id)));
    }

    /**
     * Retrieves a Question by its description.
     * 
     * @param description The description of the question to retrieve.
     * @return The found Question object.
     * @throws ResourceNotFoundException If no question with the specified description is found.
     */
    public Question getQuestionByDescription(String description) throws ResourceNotFoundException {
        return questionRepository.findByDescription(description)
            .orElseThrow(() -> new ResourceNotFoundException("Question", "Description", description));
    }
    

    /**
     * Retrieves all questions from the database.
     *
     * @return List of all Question entities.
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll(); // Returns all questions from the repository
    }
}
