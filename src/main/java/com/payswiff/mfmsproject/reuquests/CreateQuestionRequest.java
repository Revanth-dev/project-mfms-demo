package com.payswiff.mfmsproject.reuquests;

import java.util.UUID;

import com.payswiff.mfmsproject.models.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to create a new Question.
 * This class holds the necessary information to create a Question entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateQuestionRequest {
    
    /**
     * The description of the question.
     */
    private String questionDescription;

    /**
     * Converts this CreateQuestionRequest to a Question entity.
     *
     * @return A new Question entity populated with the data from this request,
     *         including a randomly generated UUID for the question.
     */
    public Question toQuestion() {
        return Question.builder()
                .questionUuid(UUID.randomUUID().toString())
                .questionDescription(questionDescription)
                .build();
    }
}
