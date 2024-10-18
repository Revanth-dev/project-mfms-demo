package com.payswiff.mfmsproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.models.Merchant;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	// Additional query methods can be defined here if needed List<Feedback>
	List<Feedback> findByFeedbackEmployee(Optional<Employee> optional);

	List<Feedback> findByFeedbackDevice(Optional<Device> deviceFromDb);

	List<Feedback> findByFeedbackMerchant(Optional<Merchant> merchantFromDb);

	List<Feedback> findByFeedbackRating(Integer rating);

	@Query("SELECT f.feedbackEmployee.employeeId AS employeeId, COUNT(f) AS feedbackCount "
			+ "FROM Feedback f GROUP BY f.feedbackEmployee.employeeId")
	List<Object[]> countFeedbacksByEmployee();

	@Query("SELECT f.feedbackDevice.deviceId AS deviceId, AVG(f.feedbackRating) AS averageRating "
			+ "FROM Feedback f GROUP BY f.feedbackDevice.deviceId")
	List<Object[]> avgRatingByDevice();
	
	@Query("SELECT f.feedbackDevice.deviceId, COUNT(f) " +
	           "FROM Feedback f " +
	           "GROUP BY f.feedbackDevice.deviceId")
	    List<Object[]> countFeedbacksByDevice();
}
