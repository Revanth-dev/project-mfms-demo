package com.payswiff.mfmsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.models.FeedbackQuestionsAssociation;
import com.payswiff.mfmsproject.models.Question;

public interface FeedbackQuestionsAssociationRepository extends JpaRepository<FeedbackQuestionsAssociation, Integer> {

	boolean existsByFeedbackAndQuestion(Feedback feedback, Question question);
    // Additional query methods can be defined here if needed
}
