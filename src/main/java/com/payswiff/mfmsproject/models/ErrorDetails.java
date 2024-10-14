package com.payswiff.mfmsproject.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the details of an error response in the system.
 * This class is used to encapsulate error information returned to the client
 * in case of exceptions or issues during API calls.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDetails {
    
    /**
     * The timestamp when the error occurred.
     * This provides context regarding when the error happened.
     */
    private Date timeStamp;

    /**
     * A message describing the error.
     * This should give a brief explanation of the issue encountered.
     */
    private String message;

    /**
     * The status code of the HTTP response.
     * This indicates the type of error based on HTTP standards.
     */
    private String statusCode;

    /**
     * Additional details about the error.
     * This may include stack traces, error codes, or other relevant information
     * that can help diagnose the problem.
     */
    private String details;
}
