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

    public Question saveQuestion(Question question) throws ResourceAlreadyExists, ResourceUnableToCreate {
        try {
            Optional<Question> existingQuestion = questionRepository.findByDescription(question.getQuestionDescription());

            if (existingQuestion.isPresent()) {
                throw new ResourceAlreadyExists("Question", "Description", question.getQuestionDescription());
            }

            return questionRepository.save(question);
        } catch (Exception e) {
            throw new ResourceUnableToCreate("Question", "Description", question.getQuestionDescription());
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
