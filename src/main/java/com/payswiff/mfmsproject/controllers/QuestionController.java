package com.payswiff.mfmsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Question;
import com.payswiff.mfmsproject.reuquests.CreateQuestionRequest;
import com.payswiff.mfmsproject.services.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

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
        Question question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
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
        Question question = questionService.getQuestionByDescription(description);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }


    /**
     * Retrieves all questions.
     *
     * @return ResponseEntity containing a list of all Question entities.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions); // Returns a 200 OK response with the list of questions
    }
}
