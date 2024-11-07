package com.payswiff.mfmsproject.controllers;

import java.util.List; // Importing List for handling collections of Question objects.

import org.springframework.beans.factory.annotation.Autowired; // Importing annotation for dependency injection.
import org.springframework.http.HttpStatus; // Importing HTTP status codes for responses.
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for building HTTP responses.
import org.springframework.web.bind.annotation.*; // Importing Spring MVC annotations for RESTful web services.

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists; // Importing exception for handling existing resource scenarios.
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing exception for handling resource not found errors.
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate; // Importing exception for handling creation errors.
import com.payswiff.mfmsproject.models.Question; // Importing Question model for handling question data.
import com.payswiff.mfmsproject.reuquests.CreateQuestionRequest; // Importing request object for creating questions.
import com.payswiff.mfmsproject.services.QuestionService; // Importing service layer for question-related logic.

/**
 * QuestionController handles requests related to questions, including creation, retrieval, 
 * and listing of questions in the application.
 */
@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.2.4:5173"})

public class QuestionController {

    @Autowired
    private QuestionService questionService; // Injecting the QuestionService for business logic

    /**
     * Creates a new Question based on the provided request details.
     *
     * @param request The request containing the details of the question to be created.
     * @return ResponseEntity containing the created Question and an HTTP status code.
     * @throws ResourceAlreadyExists If a question with the same identifier already exists.
     * @throws ResourceUnableToCreate If there is an error during the creation of the question.
     */
    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody CreateQuestionRequest request) 
            throws ResourceAlreadyExists, ResourceUnableToCreate {
        
        // Convert the CreateQuestionRequest to a Question entity using the service
        Question createdQuestion = questionService.saveQuestion(request.toQuestion());
        
        // Return a ResponseEntity with the created Question and a status of 201 Created
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    /**
     * Retrieves a Question by its ID.
     * 
     * @param id The ID of the question to retrieve.
     * @return ResponseEntity containing the found Question.
     * @throws ResourceNotFoundException if no question with the specified ID is found.
     */
    @GetMapping("/get")
    public ResponseEntity<Question> getQuestionById(@RequestParam("id") Long id) 
            throws ResourceNotFoundException {
        // Retrieve the question by ID using the service layer
        Question question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK); // Return the found question with HTTP 200 OK status
    }

    /**
     * Retrieves a Question by its description.
     * 
     * @param description The description of the question to retrieve.
     * @return ResponseEntity containing the found Question.
     * @throws ResourceNotFoundException if no question with the specified description is found.
     */
    @GetMapping("/getbydesc")
    public ResponseEntity<Question> getQuestionByDescription(@RequestParam("description") String description) 
            throws ResourceNotFoundException {
        // Retrieve the question by description using the service layer
        Question question = questionService.getQuestionByDescription(description);
        return new ResponseEntity<>(question, HttpStatus.OK); // Return the found question with HTTP 200 OK status
    }

    /**
     * Retrieves all questions.
     *
     * @return ResponseEntity containing a list of all Question entities.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        // Retrieve the list of all questions from the service layer
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions); // Returns a 200 OK response with the list of questions
    }
}
