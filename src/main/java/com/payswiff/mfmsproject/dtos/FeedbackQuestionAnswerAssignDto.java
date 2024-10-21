package com.payswiff.mfmsproject.dtos;

public class FeedbackQuestionAnswerAssignDto {
	
	private long questionId;
	
	private String questionAnswer;

	/**
	 * @return the questionId
	 */
	public long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the questionAnswer
	 */
	public String getQuestionAnswer() {
		return questionAnswer;
	}

	/**
	 * @param questionAnswer the questionAnswer to set
	 */
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	/**
	 * @param questionId
	 * @param questionAnswer
	 */
	public FeedbackQuestionAnswerAssignDto(long questionId, String questionAnswer) {
		this.questionId = questionId;
		this.questionAnswer = questionAnswer;
	}

	/**
	 * 
	 */
	public FeedbackQuestionAnswerAssignDto() {
		
	}
	
	

}
