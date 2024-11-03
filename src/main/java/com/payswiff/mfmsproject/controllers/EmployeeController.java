package com.payswiff.mfmsproject.controllers; // Package declaration for the EmployeeController

import java.util.List; // Importing List for handling lists of employees

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired for dependency injection
import org.springframework.http.HttpStatus; // Importing HttpStatus for defining HTTP response statuses
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for handling HTTP responses
import org.springframework.web.bind.annotation.*; // Importing Spring web annotations

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists; // Importing exception for handling resource existence conflicts
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing exception for handling resource not found
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.exceptions.UnableSentEmail; // Importing exception for handling email sending failures
import com.payswiff.mfmsproject.models.Employee; // Importing Employee model
import com.payswiff.mfmsproject.reuquests.CreateEmployeeRequest; // Importing DTO for employee creation request
import com.payswiff.mfmsproject.services.EmployeeService; // Importing EmployeeService to handle employee-related operations

import jakarta.validation.Valid; // Importing Valid for validating request bodies

/**
 * REST controller for handling employee-related operations such as creating
 * and retrieving employee details.
 */
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/employees") // Base URL for all employee-related APIs
@CrossOrigin(origins = "http://localhost:5173") // Allow CORS for specified origin (frontend URL)
public class EmployeeController {

    @Autowired // Automatically inject the EmployeeService bean
    private EmployeeService employeeService; // Service for handling employee-related logic

    /**
     * Creates a new Employee after checking for existing employees with the same
     * Payswiff ID, email, or phone number.
     *
     * @param request The request containing employee details.
     * @return ResponseEntity containing the created employee.
     * @throws ResourceAlreadyExists if an employee with the same Payswiff ID,
     *                               email, or phone already exists.
     * @throws ResourceNotFoundException if a related resource is not found.
     * @throws UnableSentEmail if there was an issue sending an email.
     * @throws ResourceUnableToCreate 
     */
    @PostMapping("/create") // Endpoint to create a new employee
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) 
            throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail, ResourceUnableToCreate {
        // Convert CreateEmployeeRequest to Employee model
        Employee employee = request.toEmployee();
        // Save the employee using the service and return the created employee
        Employee createdEmployee = employeeService.saveEmployee(employee);
        // Return response with created employee and 201 Created status
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /**
     * Retrieves an Employee by Payswiff ID, phone number, or email.
     *
     * @param payswiffId The Payswiff ID of the employee to retrieve (optional).
     * @param phoneNumber The phone number of the employee to retrieve (optional).
     * @param email The email of the employee to retrieve (optional).
     * @return ResponseEntity containing the found employee.
     * @throws ResourceNotFoundException if no employee is found with the provided details.
     */
    @GetMapping("/get") // Endpoint to get an employee by Payswiff ID, phone number, or email
    public ResponseEntity<Employee> getEmployee(
            @RequestParam(required = false) String payswiffId, 
            @RequestParam(required = false) String phoneNumber, 
            @RequestParam(required = false) String email) 
            throws ResourceNotFoundException {
        // Call the service method to retrieve the employee based on provided parameters
        Employee employee = employeeService.getEmployee(payswiffId, phoneNumber, email);
        // Return the found employee with 200 OK status
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    /**
     * Retrieves all Employees.
     *
     * @return ResponseEntity containing a list of all Employee entities.
     */
    @GetMapping("/all") // Endpoint to retrieve all employees
    public ResponseEntity<List<Employee>> getAllEmployees() {
        // Call the service method to retrieve all employees
        List<Employee> employees = employeeService.getAllEmployees();
        // Return the list of employees with 200 OK status
        return ResponseEntity.ok(employees);
    }
}
