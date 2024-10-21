package com.payswiff.mfmsproject.reuquests;

import java.util.List;

import com.payswiff.mfmsproject.dtos.FeedbackQuestionAnswerAssignDto;

public class FeedbackRequestWrapper {
    private CreateFeedbackRequest feedbackRequest;
    private List<FeedbackQuestionAnswerAssignDto> questionAnswers; // List for multiple questions

    // Getters and setters

    public CreateFeedbackRequest getFeedbackRequest() {
        return feedbackRequest;
    }

    public void setFeedbackRequest(CreateFeedbackRequest feedbackRequest) {
        this.feedbackRequest = feedbackRequest;
    }

    public List<FeedbackQuestionAnswerAssignDto> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<FeedbackQuestionAnswerAssignDto> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

	/**
	 * @param feedbackRequest
	 * @param questionAnswers
	 */
	public FeedbackRequestWrapper(CreateFeedbackRequest feedbackRequest,
			List<FeedbackQuestionAnswerAssignDto> questionAnswers) {
		this.feedbackRequest = feedbackRequest;
		this.questionAnswers = questionAnswers;
	}
    
}
