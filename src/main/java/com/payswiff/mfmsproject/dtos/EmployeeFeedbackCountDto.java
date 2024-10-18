package com.payswiff.mfmsproject.dtos;


public class EmployeeFeedbackCountDto {
    private Long employeeId;
    private String employeeName; // If you want to include the employee's name
    private Integer feedbackCount;
	/**
	 * @return the employeeId
	 */
	public Long getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}
	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	/**
	 * @return the feedbackCount
	 */
	public Integer getFeedbackCount() {
		return feedbackCount;
	}
	/**
	 * @param feedbackCount the feedbackCount to set
	 */
	public void setFeedbackCount(Integer feedbackCount) {
		this.feedbackCount = feedbackCount;
	}
	/**
	 * @param employeeId2
	 * @param employeeName
	 * @param l
	 */
	public EmployeeFeedbackCountDto(Long employeeId2, String employeeName, long l) {
		this.employeeId = employeeId2;
		this.employeeName = employeeName;
		this.feedbackCount = (int)l;
	}
	/**
	 * 
	 */
	public EmployeeFeedbackCountDto() {
	}
    
    
}