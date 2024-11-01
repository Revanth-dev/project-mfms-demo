package com.payswiff.mfmsproject.exceptions;

/**
 * Exception thrown when an email fails to send.
 * This exception provides information about the email address
 * to which the sending attempt was made.
 */
public class UnableSentEmail extends Exception {

    private String email; // The email address that could not be sent

    /**
     * Constructs an UnableSentEmail exception with the specified email address.
     *
     * @param email The email address that could not be sent.
     */
    public UnableSentEmail(String email) {
        super(String.format("Email is unable to send to %s", email));
        this.email = email; // Initialize the email field
    }

    /**
     * Gets the email address that could not be sent.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for this exception.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
