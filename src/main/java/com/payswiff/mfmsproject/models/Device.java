package com.payswiff.mfmsproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents a device entity in the system.
 * This class maps to the 'device' table in the database.
 */
@Entity
@Getter
@Setter
@Builder // Lombok annotation to provide a builder pattern for creating Device objects
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
@NoArgsConstructor // Lombok annotation to generate a default constructor
@Table(name = "device") // Specifies the name of the table in the database
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    @Column(name = "device_id") // Maps to the 'device_id' column in the database
    private Long deviceId;

    @Column(name = "device_uuid", nullable = false, unique = true) // Unique identifier for the device
    private String deviceUuid;

    @Column(name = "device_model", nullable = false, unique = true) // Model of the device, must be unique
    private String deviceModel;

    @Column(name = "device_manufacturer", nullable = false) // Manufacturer of the device
    private String deviceManufacturer;

    @Column(name = "device_creation_time") // Timestamp of when the device was created
    @CreationTimestamp // Automatically sets this field to the current timestamp upon creation
    private LocalDateTime deviceCreationTime;
    
    @UpdateTimestamp // Automatically updates this field to the current timestamp upon updates
    @Column(name = "device_updation_time") // Timestamp of the last update to the device
    private LocalDateTime deviceUpdationTime;

	/**
	 * @return the deviceId
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the deviceUuid
	 */
	public String getDeviceUuid() {
		return deviceUuid;
	}

	/**
	 * @param deviceUuid the deviceUuid to set
	 */
	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @param deviceManufacturer the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @return the deviceCreationTime
	 */
	public LocalDateTime getDeviceCreationTime() {
		return deviceCreationTime;
	}

	/**
	 * @param deviceCreationTime the deviceCreationTime to set
	 */
	public void setDeviceCreationTime(LocalDateTime deviceCreationTime) {
		this.deviceCreationTime = deviceCreationTime;
	}

	/**
	 * @return the deviceUpdationTime
	 */
	public LocalDateTime getDeviceUpdationTime() {
		return deviceUpdationTime;
	}

	/**
	 * @param deviceUpdationTime the deviceUpdationTime to set
	 */
	public void setDeviceUpdationTime(LocalDateTime deviceUpdationTime) {
		this.deviceUpdationTime = deviceUpdationTime;
	}

	/**
	 * @param deviceId
	 * @param deviceUuid
	 * @param deviceModel
	 * @param deviceManufacturer
	 * @param deviceCreationTime
	 * @param deviceUpdationTime
	 */
	public Device(Long deviceId, String deviceUuid, String deviceModel, String deviceManufacturer,
			LocalDateTime deviceCreationTime, LocalDateTime deviceUpdationTime) {
		this.deviceId = deviceId;
		this.deviceUuid = deviceUuid;
		this.deviceModel = deviceModel;
		this.deviceManufacturer = deviceManufacturer;
		this.deviceCreationTime = deviceCreationTime;
		this.deviceUpdationTime = deviceUpdationTime;
	}

	public Device() {
		
	}
    
}
