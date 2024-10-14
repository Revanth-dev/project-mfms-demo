package com.payswiff.mfmsproject.reuquests;

import java.util.UUID;

import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.models.EmployeeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to create a new Employee.
 * This class holds the necessary information to create an Employee entity.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeRequest {

    /**
     * The Payswiff ID assigned to the employee.
     */
    private String employeePayswiffId;

    /**
     * The name of the employee.
     */
    private String employeeName;

    /**
     * The email address of the employee.
     */
    private String employeeEmail;

    /**
     * The password for the employee account.
     */
    private String employeePassword;

    /**
     * The phone number of the employee.
     */
    private String employeePhoneNumber;

    /**
     * The designation or job title of the employee.
     */
    private String employeeDesignation;

    /**
     * The type of employee (e.g., admin, employee).
     */
    private String employeeType;

    /**
     * Converts this CreateEmployeeRequest to an Employee entity.
     *
     * @return A new Employee entity populated with the data from this request,
     *         including a randomly generated UUID for the employee.
     */
    public Employee toEmployee() {
        return Employee.builder()
                .employeeEmail(this.employeeEmail)
                .employeeUuid(UUID.randomUUID().toString())
                .employeeDesignation(this.employeeDesignation)
                .employeeName(this.employeeName)
                .employeePassword(this.employeePassword)
                .employeePayswiffId(this.employeePayswiffId)
                .employeePhoneNumber(this.employeePhoneNumber)
                .employeeType(EmployeeType.valueOf(this.employeeType))
                .build();
    }
}
