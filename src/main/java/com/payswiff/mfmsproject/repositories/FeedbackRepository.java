package com.payswiff.mfmsproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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
	
}
