package com.payswiff.mfmsproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payswiff.mfmsproject.models.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // Additional query methods can be defined here if needed
	List<Feedback> findByEmployeeId(Long employeeId);

    List<Feedback> findByDeviceId(Long deviceId);

    List<Feedback> findByRating(Integer rating);

    List<Feedback> findByMerchantId(Long merchantId);
}
