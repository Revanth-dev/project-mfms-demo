package com.payswiff.mfmsproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents an Employee entity in the system. This class is mapped to the
 * 'employee' table in the database.
 */
@Entity
@Table(name = "employee") // Specifies the name of the table in the database
@Data // Generates getter and setter methods, toString, equals, and hashCode
@Getter // Generates getter methods for all fields
@Setter // Generates setter methods for all fields
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a no-arguments constructor
@Builder // Enables the Builder pattern for this class
public class Employee {

	/**
	 * The unique identifier for the employee. This field is the primary key and is
	 * auto-generated.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the primary key value
	@Column(name = "employee_id") // Maps the field to the 'employee_id' column in the table
	private Long employeeId;

	/**
	 * The unique UUID for the employee. This field is required and must be unique.
	 */
	@Column(name = "employee_uuid", nullable = false, unique = true) // Maps to 'employee_uuid' column with unique
																		// constraint
	private String employeeUuid;

	/**
	 * The unique Payswiff ID for the employee. This field is required and must be
	 * unique.
	 */
	@Column(name = "employee_payswiff_id", nullable = false, unique = true) // Unique Payswiff ID for the employee
	private String employeePayswiffId;

	/**
	 * The name of the employee. This field is required.
	 */
	@Column(name = "employee_name", nullable = false) // Maps to 'employee_name' column, cannot be null
	private String employeeName;

	/**
	 * The email address of the employee. This field is required.
	 */
	@Column(name = "employee_email", nullable = false, unique = true) // Maps to 'employee_email' column, cannot be null
	private String employeeEmail;

	/**
	 * The password for the employee's account. This field is required.
	 */
	@Column(name = "employee_password", nullable = false) // Maps to 'employee_password' column, cannot be null
	@JsonIgnoreProperties
	@JsonIgnore
	private String employeePassword;

	/**
	 * The phone number of the employee. This field is optional.
	 */
	@Column(name = "employee_phone_number", nullable = false, unique = true) // Maps to 'employee_phone_number' column,
																				// can be null
	private String employeePhoneNumber;

	/**
	 * The designation of the employee. This field is required.
	 */
	@Column(name = "employee_designation", nullable = false) // Maps to 'employee_designation' column, cannot be null
	private String employeeDesignation;

	/**
	 * The type of employee, represented as an enum. This field is required.
	 */
	@Enumerated(EnumType.STRING) // Maps the enum type as a String in the database
	@Column(name = "employee_type", nullable = false) // Maps to 'employee_type' column, cannot be null
	private EmployeeType employeeType;

	/**
	 * The timestamp indicating when the employee record was created. This field is
	 * automatically populated when the record is created.
	 */
	@Column(name = "employee_creation_time") // Maps to 'employee_creation_time' column
	@CreationTimestamp // Automatically sets the timestamp when the entity is created
	private LocalDateTime employeeCreationTime;

	/**
	 * The timestamp indicating the last time the employee record was updated. This
	 * field is automatically populated when the record is updated.
	 */
	@UpdateTimestamp // Automatically updates the timestamp when the entity is updated
	@Column(name = "employee_updation_time") // Maps to 'employee_updation_time' column
	private LocalDateTime employeeUpdationTime;

	// roles
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "employee_roles", 
	joinColumns = @JoinColumn(referencedColumnName = "employee_id", name = "employee_id"), 
	inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "role_id"))
	private Set<Role> roles;

}
