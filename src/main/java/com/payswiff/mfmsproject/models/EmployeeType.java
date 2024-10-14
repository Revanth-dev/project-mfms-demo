package com.payswiff.mfmsproject.models;

/**
 * Represents the types of employees in the system.
 * This enum defines two types of employees:
 * <ul>
 *     <li><b>ADMIN</b>: Represents an administrative user with higher privileges.</li>
 *     <li><b>EMPLOYEE</b>: Represents a regular employee with standard access rights.</li>
 * </ul>
 */
public enum EmployeeType {
    /**
     * Represents an administrator type employee.
     * Administrators typically have elevated permissions and access to manage the system.
     */
    admin, 

    /**
     * Represents a standard employee type.
     * Regular employees have limited access compared to administrators.
     */
    employee
}
