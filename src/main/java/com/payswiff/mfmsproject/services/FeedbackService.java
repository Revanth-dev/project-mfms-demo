package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {

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

    /**
     * Method to create feedback if all associated entities are available.
     *
     * @param feedbackRequest The request object containing feedback data.
     * @return The created Feedback object if successful.
     * @throws ResourceNotFoundException if any of the entities are not found.
     * @throws ResourceAlreadyExists 
     */
    public Feedback createFeedback(CreateFeedbackRequest feedbackRequest) throws ResourceNotFoundException{
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

        // If all checks pass, create feedback
        Feedback feedback = Feedback.builder()
                .feedbackEmployee(employee)
                .feedbackMerchant(merchant)
                .feedbackDevice(device)
                .feedbackImage1(feedbackRequest.getFeedbackImage1())
                .feedbackRating(feedbackRequest.getFeedbackRating())
                .feedback(feedbackRequest.getFeedback())
                .feedbackUuid(UUID.randomUUID().toString())
                .build();

        // Save feedback to the repository
        Feedback savedFeedback = feedbackRepository.save(feedback);

        associateFeedbackWithQuestions(savedFeedback);

        return savedFeedback;
    }

    /**
     * Method to associate feedback with predefined questions and their answers.
     *
     * @param feedback The feedback object to associate with questions.
     * @throws ResourceNotFoundException 
     * @throws ResourceAlreadyExists 
     */
    private void associateFeedbackWithQuestions(Feedback feedback) throws ResourceNotFoundException {
        // Fetch the predefined questions from the database (you can customize this as per your needs)
        List<Question> predefinedQuestions = questionRepository.findAll(); // Or any specific method to fetch fixed questions

        for (Question question : predefinedQuestions) {
            // Here, we're assuming that each question has a fixed answer; you might want to customize this.
            String fixedAnswer = "Default answer"; // Replace with the actual logic for fetching the answer

            // Create the association and save it
            FeedbackQuestionsAssociation association = FeedbackQuestionsAssociation.builder()
                    .feedback(feedback)
                    .question(question)
                    .answer(fixedAnswer)
                    .build();

            // Save the association using the association service
            feedbackQuestionsAssociationService.createAssociation(association); // Make sure to implement this in your service
        }
    }
    
    /**
     * Method to filter feedback based on employeeId, deviceId, rating, or merchantId.
     * Before fetching feedback, checks if the employee, device, or merchant exists using their respective services.
     *
     * @param employeeId The ID of the employee.
     * @param deviceId The ID of the device.
     * @param rating The rating of the feedback.
     * @param merchantId The ID of the merchant.
     * @return List of feedbacks matching the criteria.
     * @throws ResourceNotFoundException If the employee, device, or merchant does not exist.
     */
    public List<Feedback> getFeedbacksByFilters(Long employeeId, Long deviceId, Integer rating, Long merchantId) throws ResourceNotFoundException {

        if (merchantId != null) {
            // Check if merchant exists using MerchantService
            if (!merchantService.existsById(merchantId)) {
                throw new ResourceNotFoundException("Merchant", "ID", String.valueOf(merchantId));
            }
            return feedbackRepository.findByMerchantId(merchantId);
        } else if (employeeId != null) {
            // Check if employee exists using EmployeeService
            if (!employeeService.existsById(employeeId)) {
                throw new ResourceNotFoundException("Employee","ID", String.valueOf(employeeId));
            }
            return feedbackRepository.findByFeedbackEmployee(employeeRepository.findById(employeeId));
        } else if (deviceId != null) {
            // Check if device exists using DeviceService
            if (!deviceService.existsById(deviceId)) {
                throw new ResourceNotFoundException("Device", "ID", String.valueOf(deviceId));
            }
            return feedbackRepository.findByDeviceId(deviceId);
        } else if (rating != null) {
            // Find feedback by rating
            return feedbackRepository.findByRating(rating);
        } else {
            List<Feedback> feedbacks = feedbackRepository.findAll();
           
            return feedbacks;
        }
    }

    
}
