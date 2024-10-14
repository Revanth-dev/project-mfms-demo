package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.models.Role;
import com.payswiff.mfmsproject.repositories.EmployeeRepository;
import com.payswiff.mfmsproject.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleRepository;

    /**
     * Saves a new Employee after checking if an employee with the same Payswiff ID, email, or phone number already exists.
     *
     * @param employee The employee entity to be saved.
     * @return The saved Employee object.
     * @throws ResourceAlreadyExists If an employee with the same Payswiff ID, email, or phone number already exists.
     * @throws ResourceNotFoundException 
     */
    public Employee saveEmployee(Employee employee) throws ResourceAlreadyExists, ResourceNotFoundException {
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
        //encrypt the employee password
        employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword()));
        
        Set<Role> roles = new HashSet<>();
        Role employeeRole;

        // Check the employee type and assign the correct role
        if ("admin".equals(employee.getEmployeeType())) {
            // Find role "ROLE_admin" in the database
            employeeRole = roleRepository.findByName("ROLE_admin")
                .orElseThrow(() -> new ResourceNotFoundException("Role" ,"Name","ROLE_admin"));
        } else {
            // Find role "ROLE_employee" in the database
            employeeRole = roleRepository.findByName("ROLE_employee")
                .orElseThrow(() -> new ResourceNotFoundException("Role" ,"Name","ROLE_employee"));
        }

        // Add the role to the employee's role set
        roles.add(employeeRole);

        // Assign roles to the employee
        employee.setRoles(roles);

        // Save the employee (if necessary)
       
        
        return employeeRepository.save(employee);
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
        return employeeId != null && employeeRepository.existsById(employeeId); // Use repository to check if employee exists
    }
}