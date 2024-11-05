package com.payswiff.mfmsproject.controllers;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.exceptions.UnableSentEmail;
import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.reuquests.CreateEmployeeRequest;
import com.payswiff.mfmsproject.services.EmployeeService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for EmployeeController.
 * This class tests the create, retrieve, and list employee functionalities of the EmployeeController.
 */
class EmployeeControllerTest {

    @Mock // Mocking the EmployeeService
    private EmployeeService employeeService;

    @InjectMocks // Injecting the mocked EmployeeService into EmployeeController
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test method for successfully creating an employee.
     * Verifies that the controller returns a 201 Created response with the created employee.
     */
    @Test
    void testCreateEmployee_Success() throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail, ResourceUnableToCreate {
        // Arrange
        CreateEmployeeRequest request = new CreateEmployeeRequest(12345, "John Doe", "john@example.com", "1234567890","Dev","employee","passowrd");
        Employee createdEmployee = new Employee(null,UUID.randomUUID().toString(),"Payswiff ID", "John Doe", "john@example.com", "1234567890","Dev","employee","passowrd",null,null);
        
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(createdEmployee); // Mocking service response

        // Act
        ResponseEntity<Employee> response = employeeController.createEmployee(request);

        // Assert
        assertEquals(201, response.getStatusCodeValue()); // Check for HTTP 201 Created
        assertEquals(createdEmployee, response.getBody()); // Validate response body
    }

    /**
     * Test method for creating an employee that already exists.
     * Verifies that the controller throws ResourceAlreadyExists exception.
     */
    @Test
    void testCreateEmployee_AlreadyExists() throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail, ResourceUnableToCreate {
        // Arrange
        CreateEmployeeRequest request = new CreateEmployeeRequest("Payswiff ID", "Jane Doe", "jane@example.com", "0987654321");
        when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new ResourceAlreadyExists("Employee already exists"));

        // Act & Assert
        ResourceAlreadyExists exception = assertThrows(ResourceAlreadyExists.class, () -> {
            employeeController.createEmployee(request);
        });
        assertEquals("Employee already exists", exception.getMessage()); // Validate exception message
    }

    /**
     * Test method for creating an employee when an email cannot be sent.
     * Verifies that the controller throws UnableSentEmail exception.
     */
    @Test
    void testCreateEmployee_EmailSendingFailure() throws ResourceAlreadyExists, ResourceNotFoundException, UnableSentEmail, ResourceUnableToCreate {
        // Arrange
        CreateEmployeeRequest request = new CreateEmployeeRequest("Payswiff ID", "Jake Doe", "jake@example.com", "1122334455");
        when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new UnableSentEmail("Email sending failed"));

        // Act & Assert
        UnableSentEmail exception = assertThrows(UnableSentEmail.class, () -> {
            employeeController.createEmployee(request);
        });
        assertEquals("Email sending failed", exception.getMessage()); // Validate exception message
    }

    /**
     * Test method for creating an employee with invalid input.
     * Verifies that the controller throws ResourceUnableToCreate exception.
     */
    @Test
    void testCreateEmployee_InvalidInput() {
        // Arrange
        CreateEmployeeRequest request = null; // Simulating invalid input (null request)

        // Act & Assert
        assertThrows(ResourceUnableToCreate.class, () -> {
            employeeController.createEmployee(request); // Expect exception for null input
        });
    }

    /**
     * Test method for retrieving an employee by Payswiff ID.
     * Verifies that the controller returns the employee with a 200 OK response.
     */
    @Test
    void testGetEmployee_ByPayswiffId_Success() throws ResourceNotFoundException {
        // Arrange
        String payswiffId = "Payswiff ID";
        Employee employee = new Employee(payswiffId, "John Doe", "john@example.com", "1234567890");
        when(employeeService.getEmployee(payswiffId, null, null)).thenReturn(employee); // Mocking service response

        // Act
        ResponseEntity<Employee> response = employeeController.getEmployee(payswiffId, null, null);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Check for HTTP 200 OK
        assertEquals(employee, response.getBody()); // Validate response body
    }

    /**
     * Test method for retrieving an employee by email.
     * Verifies that the controller returns the employee with a 200 OK response.
     */
    @Test
    void testGetEmployee_ByEmail_Success() throws ResourceNotFoundException {
        // Arrange
        String email = "john@example.com";
        Employee employee = new Employee("Payswiff ID", "John Doe", email, "1234567890");
        when(employeeService.getEmployee(null, null, email)).thenReturn(employee); // Mocking service response

        // Act
        ResponseEntity<Employee> response = employeeController.getEmployee(null, null, email);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Check for HTTP 200 OK
        assertEquals(employee, response.getBody()); // Validate response body
    }

    /**
     * Test method for retrieving an employee that does not exist.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testGetEmployee_NotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(employeeService.getEmployee(null, null, email)).thenThrow(new ResourceNotFoundException("Employee not found","",""));

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee(null, null, email);
        });
        assertEquals("Employee not found", exception.getMessage()); // Validate exception message
    }

    /**
     * Test method for retrieving an employee with null parameters.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testGetEmployee_NullParameters() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee(null, null, null); // Expect exception for null input
        });
    }

    /**
     * Test method for successfully retrieving all employees.
     * Verifies that the controller returns a list of employees with a 200 OK response.
     */
    @Test
    void testGetAllEmployees_Success() {
        // Arrange
        Employee employee1 = new Employee("Payswiff ID 1", "John Doe", "john@example.com", "1234567890",);
        Employee employee2 = new Employee("Payswiff ID 2", "Jane Doe", "jane@example.com", "0987654321");
        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employeeList); // Mocking service response

        // Act
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Check for HTTP 200 OK
        assertEquals(employeeList, response.getBody()); // Validate response body
    }

    /**
     * Test method for retrieving all employees when none exist.
     * Verifies that the controller returns an empty list with a 200 OK response.
     */
    @Test
    void testGetAllEmployees_EmptyList() {
        // Arrange
        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList()); // Mocking service response

        // Act
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Check for HTTP 200 OK
        assertTrue(response.getBody().isEmpty()); // Validate response body is empty
    }

    /**
     * Test method for retrieving all employees when there is an error.
     * Verifies that the controller handles exceptions gracefully.
     */
    @Test
    void testGetAllEmployees_Error() {
        // Arrange
        when(employeeService.getAllEmployees()).thenThrow(new RuntimeException("Unexpected error")); // Mocking error

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeController.getAllEmployees(); // Expect exception for service error
        });
        assertEquals("Unexpected error", exception.getMessage()); // Validate exception message
    }

    /**
     * Test method for handling null employee retrieval.
     * Verifies that the controller throws ResourceNotFoundException when called with null parameters.
     */
    @Test
    void testGetEmployee_NullRequestParameters() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee(null, null, null); // Expect exception for null parameters
        });
    }

    /**
     * Test method for checking behavior when an employee's Payswiff ID is null during retrieval.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testGetEmployee_NullPayswiffId() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee(null, "1234567890", null); // Expect exception for null Payswiff ID
        });
    }

    /**
     * Test method for checking behavior when an employee's phone number is null during retrieval.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testGetEmployee_NullPhoneNumber() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee("Payswiff ID", null, null); // Expect exception for null phone number
        });
    }

    /**
     * Test method for checking behavior when an employee's email is null during retrieval.
     * Verifies that the controller throws ResourceNotFoundException.
     */
    @Test
    void testGetEmployee_NullEmail() {
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeController.getEmployee(null, null, "john@example.com"); // Expect exception for null email
        });
    }

    /**
     * Test method for verifying the EmployeeController class is correctly instantiated.
     */
    @Test
    void testEmployeeControllerInstantiation() {
        // Act & Assert
        assertNotNull(employeeController); // Ensure the EmployeeController is instantiated
    }
}
