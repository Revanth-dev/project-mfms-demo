package com.payswiff.mfmsproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.payswiff.mfmsproject.models.ErrorDetails;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                String.valueOf(HttpStatus.NOT_FOUND), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle ResourceAlreadyExists Exception
    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorDetails> handleResourceAlreadyExists(
            ResourceAlreadyExists ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                String.valueOf(HttpStatus.CONFLICT), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    // Handle ResourceUnableToCreate Exception
    @ExceptionHandler(ResourceUnableToCreate.class)
    public ResponseEntity<ErrorDetails> handleResourceUnableToCreateException(
            ResourceUnableToCreate ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Generic exception handler (optional, for handling any unhandled exceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MerchantDeviceNotAssignedException.class)
    public ResponseEntity<ErrorDetails> handleMerchnatDeviceNotAssignedException(MerchantDeviceNotAssignedException e,
    		WebRequest request){
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(EmployeePasswordUpdationFailedException.class)
    public ResponseEntity<ErrorDetails> handleEmployeePasswordUpdationFailedException(EmployeePasswordUpdationFailedException e,
    		WebRequest request){
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(UnableSentEmail.class)
    public ResponseEntity<ErrorDetails> handleUnableSentEmail(UnableSentEmail e,
    		WebRequest request){
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), request.getDescription(false));
        
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
