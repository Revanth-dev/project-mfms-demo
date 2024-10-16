package com.payswiff.mfmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.UnableSentEmail;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.reuquests.CreateEmployeeRequest;
import com.payswiff.mfmsproject.services.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates a new Employee after checking for existing employees with the same Payswiff ID, email, or phone number.
     *
     * @param request The request containing employee details.
     * @return ResponseEntity containing the created employee.
     * @throws ResourceAlreadyExists if an employee with the same Payswiff ID, email, or phone already exists.
     * @throws ResourceNotFoundException 
     * @throws UnableSentEmail 
     */
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) 
            throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail {
        Employee employee = request.toEmployee();
        Employee createdEmployee = employeeService.saveEmployee(employee);
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
    @GetMapping("/get")
    public ResponseEntity<Employee> getEmployee(
            @RequestParam(required = false) String payswiffId, 
            @RequestParam(required = false) String phoneNumber, 
            @RequestParam(required = false) String email) 
            throws ResourceNotFoundException {
        Employee employee = employeeService.getEmployee(payswiffId, phoneNumber, email);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
