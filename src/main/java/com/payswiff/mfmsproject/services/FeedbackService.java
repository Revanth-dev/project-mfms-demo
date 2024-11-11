package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.models.Feedback;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.models.Merchant;
import com.payswiff.mfmsproject.models.Question; // Import the Question model
import com.payswiff.mfmsproject.models.FeedbackQuestionsAssociation; // Import the association model
import com.payswiff.mfmsproject.repositories.FeedbackRepository;
import com.payswiff.mfmsproject.repositories.EmployeeRepository;
import com.payswiff.mfmsproject.repositories.DeviceRepository;
import com.payswiff.mfmsproject.repositories.MerchantRepository;
import com.payswiff.mfmsproject.repositories.QuestionRepository; // Import the Question repository
import com.payswiff.mfmsproject.reuquests.CreateFeedbackRequest;
import com.payswiff.mfmsproject.dtos.AverageRatingResponseDTO;
import com.payswiff.mfmsproject.dtos.DeviceFeedbackCountDTO;
import com.payswiff.mfmsproject.dtos.EmailSendDto;
import com.payswiff.mfmsproject.dtos.EmployeeFeedbackCountDto;
import com.payswiff.mfmsproject.dtos.FeedbackQuestionAnswerAssignDto;
import com.payswiff.mfmsproject.exceptions.MerchantDeviceNotAssignedException;
import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.exceptions.UnableSentEmail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

@Service
public class FeedbackService {
	
    private static final Logger logger =LogManager.getLogger(FeedbackService.class); // Logger initialization


	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private QuestionRepository questionRepository; // Inject Question repository

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private FeedbackQuestionsAssociationService feedbackQuestionsAssociationService; // Inject the association service

	@Autowired
	private MerchantDeviceAssociationService merchantDeviceAssociationService;

	@Autowired
	private EmailService emailService;

	/**
	 * Method to create feedback if all associated entities are available.
	 *
	 * @param feedbackRequest The request object containing feedback data.
	 * @param questionAnswers List of feedback question answers to associate with the feedback.
	 * @return true if feedback creation is successful.
	 * @throws Exception 
	 */
	public boolean createFeedback(CreateFeedbackRequest feedbackRequest,
	                              List<FeedbackQuestionAnswerAssignDto> questionAnswers)
	        throws Exception {

	    try {
	        // Check if the employee exists
	        Optional<Employee> employeeOptional = employeeRepository.findById(feedbackRequest.getFeedbackEmployeeId());
	        if (employeeOptional.isEmpty()) {
	            throw new ResourceNotFoundException("Employee", "ID", feedbackRequest.getFeedbackEmployeeId().toString());
	        }
	        Employee employee = employeeOptional.get();

	        // Check if the merchant exists
	        Optional<Merchant> merchantOptional = merchantRepository.findById(feedbackRequest.getFeedbackMerchantId());
	        if (merchantOptional.isEmpty()) {
	            throw new ResourceNotFoundException("Merchant", "ID", feedbackRequest.getFeedbackMerchantId().toString());
	        }
	        Merchant merchant = merchantOptional.get();

	        // Check if the device exists
	        Optional<Device> deviceOptional = deviceRepository.findById(feedbackRequest.getFeedbackDeviceId());
	        if (deviceOptional.isEmpty()) {
	            throw new ResourceNotFoundException("Device", "ID", feedbackRequest.getFeedbackDeviceId().toString());
	        }
	        Device device = deviceOptional.get();

	        // Check whether the merchant has the given device or not
	        if (!merchantDeviceAssociationService.isDeviceAssociatedWithMerchant(feedbackRequest.getFeedbackMerchantId(),
	                feedbackRequest.getFeedbackDeviceId())) {
	            throw new MerchantDeviceNotAssignedException("Device", "ID",
	                    String.valueOf(feedbackRequest.getFeedbackDeviceId()), "Merchant", "ID",
	                    String.valueOf(feedbackRequest.getFeedbackMerchantId()));
	        }

	        // If all checks pass, create feedback
	        Feedback feedback = new Feedback();
	        feedback.setFeedbackEmployee(employee);
	        feedback.setFeedbackDevice(device);
	        feedback.setFeedbackMerchant(merchant);
	        feedback.setFeedbackRating(feedbackRequest.getFeedbackRating());
	        feedback.setFeedbackUuid(UUID.randomUUID().toString());
	        feedback.setFeedbackImage1(feedbackRequest.getFeedbackImage1());
	        feedback.setFeedback(feedbackRequest.getFeedback());

	        // Save feedback to the repository
	        Feedback savedFeedback = feedbackRepository.save(feedback);

	        // Associate questions with created feedback
	        associateFeedbackWithQuestions(savedFeedback, questionAnswers);

	        // Send email to employee for feedback status and feedback details
	        sendSuccessEmail(employee, savedFeedback);

	        return true;

	    } catch (ResourceNotFoundException | MerchantDeviceNotAssignedException | UnableSentEmail e) {
	        // Send failure email to inform about the error
	    	Optional<Employee> employeeOptional = employeeRepository.findById(feedbackRequest.getFeedbackEmployeeId());
	        if (employeeOptional.isEmpty()) {
	            throw new ResourceNotFoundException("Employee", "ID", feedbackRequest.getFeedbackEmployeeId().toString());
	        }
	        Employee employee = employeeOptional.get();
	        sendFailureEmail(feedbackRequest,employee ,e);

	        // Rethrow the exception to maintain the original behavior
	        throw e;
	    }
	}

	/**
	 * Method to send a success email to the employee once feedback is successfully created.
	 *
	 * @param employee The employee to send the email to.
	 * @param feedback The feedback details to include in the email.
	 */
	private void sendSuccessEmail(Employee employee, Feedback feedback) throws UnableSentEmail {
	    EmailSendDto emailSendDto = new EmailSendDto();
	    emailSendDto.setTo(employee.getEmployeeEmail());
	    emailSendDto.setSubject("Merchant Feedback Management System");

	    String emailContent = "Feedback Creation Status: Created\n" +
	            "\n" +
	            "---------------------------------------------------\n" +
	            "Feedback Details\n" +
	            "---------------------------------------------------\n" +
	            "Feedback ID: " + feedback.getFeedbackId() + "\n" +
	            "Feedback UUID: " + feedback.getFeedbackUuid() + "\n" +
	            "Employee: " + feedback.getFeedbackEmployee().getEmployeeName() + "\n" +
	            "Feedback Rating: " + feedback.getFeedbackRating() + "\n" +
	            "Feedback: " + feedback.getFeedback() + "\n" +
	            "Feedback Image: " + (feedback.getFeedbackImage1() != null ? "Available" : "Not Provided") + "\n" +
	            "\n" +
	            "---------------------------------------------------\n" +
	            "Device Details\n" +
	            "---------------------------------------------------\n" +
	            "Device ID: " + feedback.getFeedbackDevice().getDeviceId() + "\n" +
	            "Device UUID: " + feedback.getFeedbackDevice().getDeviceUuid() + "\n" +
	            "Device Model: " + feedback.getFeedbackDevice().getDeviceModel() + "\n" +
	            "Device Manufacturer: " + feedback.getFeedbackDevice().getDeviceManufacturer() + "\n" +
	            "\n" +
	            "---------------------------------------------------\n" +
	            "Merchant Details\n" +
	            "---------------------------------------------------\n" +
	            "Merchant UUID: " + feedback.getFeedbackMerchant().getMerchantUuid() + "\n" +
	            "Merchant Email: " + feedback.getFeedbackMerchant().getMerchantEmail() + "\n" +
	            "Merchant Phone: " + feedback.getFeedbackMerchant().getMerchantPhone() + "\n" +
	            "Merchant Business Name: " + feedback.getFeedbackMerchant().getMerchantBusinessName() + "\n" +
	            "Merchant Business Type: " + feedback.getFeedbackMerchant().getMerchantBusinessType() + "\n";

	    emailSendDto.setText(emailContent);

	    boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(),
	            emailSendDto.getText());

	    if (!emailSent) {
	        throw new UnableSentEmail(employee.getEmployeeEmail());
	    }
	}

	/**
	 * Method to send a failure email in case feedback creation fails.
	 *
	 * @param feedbackRequest The feedback request object.
	 * @param employee 
	 * @param e              The exception that occurred.
	 * @throws Exception 
	 */
	private void sendFailureEmail(CreateFeedbackRequest feedbackRequest, Employee employee, Exception e) throws Exception {
	    EmailSendDto emailSendDto = new EmailSendDto();
	    emailSendDto.setTo(employee.getEmployeeEmail());  // Send email to admin or appropriate address
	    emailSendDto.setSubject("Feedback Creation Failed");

	    String emailContent = "Dear Admin,\n\n" +
	            "We encountered an error while creating feedback.\n" +
	            "Error: " + e.getClass().getSimpleName() + "\n" +
	            "Details: " + e.getMessage() + "\n\n" +
	            "Feedback Request Details:\n" +
	            "Employee ID: " + feedbackRequest.getFeedbackEmployeeId() + "\n" +
	            "Merchant ID: " + feedbackRequest.getFeedbackMerchantId() + "\n" +
	            "Device ID: " + feedbackRequest.getFeedbackDeviceId() + "\n" +
	            "Rating: " + feedbackRequest.getFeedbackRating() + "\n";

	    emailSendDto.setText(emailContent);

	    try {
	        emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());
	    } catch (Exception emailException) {
	        // Log the error if email fails to send
	        throw new Exception("failed to send email: "+emailException.getMessage());
	    }
	}


	/**
	 * Method to associate feedback with predefined questions and their answers.
	 *
	 * @param feedback        The feedback object to associate with questions.
	 * @param questionAnswers
	 * @throws ResourceNotFoundException
	 * @throws ResourceUnableToCreate 
	 * @throws ResourceAlreadyExists
	 */
	private void associateFeedbackWithQuestions(Feedback feedback,
			List<FeedbackQuestionAnswerAssignDto> questionAnswers) throws ResourceNotFoundException, ResourceUnableToCreate {

		// Fetch the predefined questions from the database
		List<Question> predefinedQuestions = questionRepository.findAll();

		// Convert the list of questionAnswers to a map for faster lookups (questionId
		// -> questionAnswer)
		Map<Long, String> questionAnswerMap = questionAnswers.stream().collect(Collectors.toMap(
				FeedbackQuestionAnswerAssignDto::getQuestionId, FeedbackQuestionAnswerAssignDto::getQuestionAnswer));

		// Iterate over the predefined questions
		for (Question question : predefinedQuestions) {
			// Get the answer for the current question from the map, if it exists
			String answerForQuestion = questionAnswerMap.getOrDefault(question.getQuestionId(), "No answer provided");

			// Create the association and save it
			FeedbackQuestionsAssociation association = new FeedbackQuestionsAssociation();
			association.setFeedback(feedback); // Set the feedback object
			association.setQuestion(question); // Set the question object
			association.setAnswer(answerForQuestion); // Set the actual answer or default value

			// Save the association using the association service
			feedbackQuestionsAssociationService.createAssociation(association);
		}
	}

	/**
	 * Method to filter feedback based on employeeId, deviceId, rating, or
	 * merchantId. Before fetching feedback, checks if the employee, device, or
	 * merchant exists using their respective services.
	 *
	 * @param employeeId The ID of the employee.
	 * @param deviceId   The ID of the device.
	 * @param rating     The rating of the feedback.
	 * @param merchantId The ID of the merchant.
	 * @return List of feedbacks matching the criteria.
	 * @throws ResourceNotFoundException If the employee, device, or merchant does
	 *                                   not exist.
	 */
	public List<Feedback> getFeedbacksByFilters(Long employeeId, Long deviceId, Integer rating, Long merchantId)
			throws ResourceNotFoundException {

		if (merchantId != null) {
			// Check if merchant exists using MerchantService
			if (!merchantService.existsById(merchantId)) {
				throw new ResourceNotFoundException("Merchant", "ID", String.valueOf(merchantId));
			}
			Optional<Merchant> merchantFromDb = merchantRepository.findById(merchantId);

			return feedbackRepository.findByFeedbackMerchant(merchantFromDb);
		} else if (employeeId != null) {
			// Check if employee exists using EmployeeService
			if (!employeeService.existsById(employeeId)) {
				throw new ResourceNotFoundException("Employee", "ID", String.valueOf(employeeId));
			}
			Optional<Employee> employeeFromDb = employeeRepository.findById(employeeId);

			return feedbackRepository.findByFeedbackEmployee(employeeFromDb);
		} else if (deviceId != null) {
			// Check if device exists using DeviceService
			if (!deviceService.existsById(deviceId)) {
				throw new ResourceNotFoundException("Device", "ID", String.valueOf(deviceId));
			}
			Optional<Device> deviceFromDb = deviceRepository.findById(deviceId);
			return feedbackRepository.findByFeedbackDevice(deviceFromDb);
		} else if (rating != null) {
			// Find feedback by rating
			return feedbackRepository.findByFeedbackRating(rating);
		} else {
			List<Feedback> feedbacks = feedbackRepository.findAll();

			return feedbacks;
		}
	}

	 /**
     * Counts the number of feedbacks for all employees.
     *
     * @return A list of EmployeeFeedbackCountDto containing employee ID, email, and feedback count.
     */
    public List<EmployeeFeedbackCountDto> countFeedbacksForAllEmployees() {
        // Retrieve feedback counts grouped by employee from the repository
        List<Object[]> results = feedbackRepository.countFeedbacksByEmployee();
        List<EmployeeFeedbackCountDto> feedbackCounts = new ArrayList<>();

        // Iterate over the results and map them to DTOs
        for (Object[] result : results) {
            Long employeeId = (Long) result[0]; // Extract the employee ID from the result
            Long feedbackCount = (Long) result[1]; // Extract the feedback count from the result

            // Optionally retrieve the employee details for email
            Employee employee = employeeRepository.findById(employeeId).orElse(null);
            String employeeEmail = (employee != null) ? employee.getEmployeeEmail() : "Unknown"; // Default to "Unknown" if not found

            // Create a new DTO and add it to the list
            feedbackCounts.add(new EmployeeFeedbackCountDto(employeeId, employeeEmail, feedbackCount.longValue()));
        }

        return feedbackCounts; // Return the list of feedback counts
    }

    /**
     * Retrieves the average rating of feedback grouped by device.
     *
     * @return A list of AverageRatingResponseDTO containing device ID and average rating.
     */
    public List<AverageRatingResponseDTO> getAverageRatingByDevice() {
        // Retrieve average ratings grouped by device from the repository
        List<Object[]> results = feedbackRepository.avgRatingByDevice();
        List<AverageRatingResponseDTO> averageRatings = new ArrayList<>();

        // Iterate over the results and map them to DTOs
        for (Object[] result : results) {
            Long deviceId = (Long) result[0]; // Extract the device ID from the result
            Double averageRating = ((Number) result[1]).doubleValue(); // Cast to Number and then to Double
            
            // Create a new DTO and add it to the list
            averageRatings.add(new AverageRatingResponseDTO(deviceId, averageRating));
        }

        return averageRatings; // Return the list of average ratings
    }

    /**
     * Counts the number of feedbacks for each device.
     *
     * @return A list of DeviceFeedbackCountDTO containing device ID and feedback count.
     */
    public List<DeviceFeedbackCountDTO> getFeedbackCountByDevice() {
        logger.info("Fetching feedback counts grouped by device.");

        List<DeviceFeedbackCountDTO> feedbackCounts = new ArrayList<>();

        try {
            // Retrieve feedback counts grouped by device from the repository
            List<Object[]> results = feedbackRepository.countFeedbacksByDevice();
            logger.info("Successfully retrieved feedback counts from repository. Results size: " + results.size());

            // Iterate over the results and map them to DTOs
            for (Object[] result : results) {
                Long deviceId = (Long) result[0]; // Extract the device ID from the result
                Long count = (Long) result[1]; // Extract the feedback count from the result

                // Log each extracted value
                logger.debug("Processing feedback count for deviceId: " + deviceId + ", count: " + count);

                // Create a new DTO and add it to the list
                feedbackCounts.add(new DeviceFeedbackCountDTO(deviceId, count));
            }

            logger.info("Feedback counts successfully mapped to DTOs. Total counts: " + feedbackCounts.size());

        } catch (Exception e) {
            logger.error("Error occurred while retrieving feedback counts by device.", e);
        }

        return feedbackCounts; // Return the list of feedback counts
    }

	public boolean checkFeedbackIntegrity(String feedbackText) {
		// TODO Auto-generated method stub
		return false;
	}
}
