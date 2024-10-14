package com.payswiff.mfmsproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a question entity in the system.
 * This class is mapped to the 'question' table in the database.
 */
@Entity
@Table(name = "question")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    /**
     * The unique identifier for the question.
     * This is automatically generated and used as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    /**
     * A unique identifier for the question, used for reference purposes.
     * This field must be provided and must be unique.
     */
    @Column(name = "question_uuid", nullable = false, unique = true)
    private String questionUuid;

    /**
     * The description of the question.
     * This field is mandatory and must be unique.
     */
    @Column(name = "question_description", nullable = false, unique = true)
    private String questionDescription;
}
