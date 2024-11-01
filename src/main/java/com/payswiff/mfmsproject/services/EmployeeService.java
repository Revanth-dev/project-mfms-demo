package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.dtos.EmailSendDto;
import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.UnableSentEmail;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.models.Role;
import com.payswiff.mfmsproject.repositories.EmployeeRepository;
import com.payswiff.mfmsproject.repositories.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository; // Repository for Employee entity
    
    @Autowired
    PasswordEncoder passwordEncoder; // For encoding passwords
    
    @Autowired
    RoleRepository roleRepository; // Repository for Role entity
    
    @Autowired
    EmailService emailService; // Service for sending emails

    /**
     * Saves a new Employee after checking if an employee with the same Payswiff ID, email, or phone number already exists.
     *
     * @param employee The employee entity to be saved.
     * @return The saved Employee object.
     * @throws ResourceAlreadyExists If an employee with the same Payswiff ID, email, or phone number already exists.
     * @throws ResourceNotFoundException If the specified role does not exist.
     * @throws UnableSentEmail If there is an issue sending the email.
     */
    public Employee saveEmployee(Employee employee) throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail {
        // Check for existing employee with the same Payswiff ID
        if (employeeRepository.findByEmployeePayswiffId(employee.getEmployeePayswiffId()).isPresent()) {
            throw new ResourceAlreadyExists("Employee", "Payswiff ID", employee.getEmployeePayswiffId());
        }
        // Check for existing employee with the same email
        if (employeeRepository.findByEmployeeEmail(employee.getEmployeeEmail()).isPresent()) {
            throw new ResourceAlreadyExists("Employee", "Email", employee.getEmployeeEmail());
        }
        // Check for existing employee with the same phone number
        if (employee.getEmployeePhoneNumber() != null && 
            employeeRepository.findByEmployeePhoneNumber(employee.getEmployeePhoneNumber()).isPresent()) {
            throw new ResourceAlreadyExists("Employee", "Phone Number", employee.getEmployeePhoneNumber());
        }

        // Encrypt the employee password before saving
        String password = employee.getEmployeePassword(); // Store the raw password for email
        employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword())); // Encode and set the password

        Set<Role> roles = new HashSet<>(); // Set to hold employee roles
        Role employeeRole;

        // Check the employee type and assign the correct role
        if ("admin".equals(employee.getEmployeeType().name().toLowerCase())) {
            // Find role "ROLE_admin" in the database
            employeeRole = roleRepository.findByName("ROLE_admin")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Name", "ROLE_admin"));
        } else {
            // Find role "ROLE_employee" in the database
            employeeRole = roleRepository.findByName("ROLE_employee")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Name", "ROLE_employee"));
        }

        // Add the role to the employee's role set
        roles.add(employeeRole);

        // Assign roles to the employee
        employee.setRoles(roles);

        // Save the employee in the database
        Employee createdEmployee = employeeRepository.save(employee);
        
        // Prepare email details
        EmailSendDto emailSendDto = new EmailSendDto();
        emailSendDto.setTo(employee.getEmployeeEmail());
        emailSendDto.setSubject("Merchant Feedback Management System");
        emailSendDto.setText("Your Account has been created successfully.\n"
        		+ "Your Login Credentials are:\n"
        		+ "Email: " + employee.getEmployeeEmail() + "\n"
        		+ "Password: " + password); // Use raw password for the email

        // Send email notification
        boolean emailSent = emailService.sendEmail(emailSendDto.getTo(), emailSendDto.getSubject(), emailSendDto.getText());
        
        // Check if the email was sent successfully
        if (!emailSent) {
        	// Email sending failed, throw an exception
        	throw new UnableSentEmail(employee.getEmployeeEmail());
        }
        
        return createdEmployee; // Return the created employee object
    }

    /**
     * Retrieves an Employee by Payswiff ID, phone number, or email.
     *
     * @param payswiffId The Payswiff ID of the employee to retrieve.
     * @param phoneNumber The phone number of the employee to retrieve.
     * @param email The email of the employee to retrieve.
     * @return The Employee object if found.
     * @throws ResourceNotFoundException If no employee is found with the provided details.
     */
    public Employee getEmployee(String payswiffId, String phoneNumber, String email) throws ResourceNotFoundException {
        // Attempt to find by Payswiff ID
        if (payswiffId != null) {
            return employeeRepository.findByEmployeePayswiffId(payswiffId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Payswiff ID", payswiffId));
        }

        // Attempt to find by Phone Number
        if (phoneNumber != null) {
            return employeeRepository.findByEmployeePhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Phone Number", phoneNumber));
        }

        // Attempt to find by Email
        if (email != null) {
            return employeeRepository.findByEmployeeEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Email", email));
        }

        // If none of the parameters are provided, throw an exception
        throw new ResourceNotFoundException("Employee", "Parameters", "None provided");
    }

    /**
     * Checks if an employee exists by its ID.
     *
     * @param employeeId The ID of the employee.
     * @return true if an employee with the given ID exists, false otherwise.
     */
    public boolean existsById(Long employeeId) {
        // Use repository to check if employee exists
        return employeeId != null && employeeRepository.existsById(employeeId);
    }
    
    /**
     * Updates the password of an employee identified by their email.
     *
     * @param email The email of the employee whose password is to be updated.
     * @param newPassword The new password to be set.
     * @return true if the password was updated successfully.
     * @throws ResourceNotFoundException If no employee is found with the given email.
     */
    public boolean updateEmployeePassword(String email, String newPassword) throws ResourceNotFoundException {
        // Fetch the employee by email
        Employee employee = employeeRepository.findByEmployeeEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Email", email));

        // Hash the new password
        String hashedPassword = passwordEncoder.encode(newPassword);

        // Update the employee's password
        employee.setEmployeePassword(hashedPassword);

        // Save the updated employee entity
        employeeRepository.save(employee);
        
        return true; // Indicate success
    }
    
    /**
     * Retrieves all employees from the database.
     *
     * @return A list of all Employee objects.
     */
    public List<Employee> getAllEmployees() {
        // Retrieve all employees
        return employeeRepository.findAll();
    }
}
