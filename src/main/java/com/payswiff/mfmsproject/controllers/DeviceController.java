package com.payswiff.mfmsproject.controllers; // Package declaration for the controllers

import java.util.List; // Importing List for handling collections of devices

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired for dependency injection
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for HTTP response handling
import org.springframework.security.access.prepost.PreAuthorize; // Importing PreAuthorize for method-level security
import org.springframework.web.bind.annotation.*; // Importing Spring web annotations

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists; // Importing custom exception for resource existence checks
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing custom exception for resource not found scenarios
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate; // Importing custom exception for creation errors
import com.payswiff.mfmsproject.models.Device; // Importing Device model
import com.payswiff.mfmsproject.reuquests.CreateDeviceRequest; // Importing DTO for device creation requests
import com.payswiff.mfmsproject.services.DeviceService; // Importing service for device operations

/**
 * REST controller for managing device-related operations.
 * This includes creating new devices and retrieving device information.
 */
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/devices") // Base URL for all device-related APIs
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.2.4:5173"})
public class DeviceController {

    @Autowired // Automatically inject the DeviceService bean
    private DeviceService deviceService; // Service for handling device-related logic

    /**
     * Creates a new device.
     *
     * @param request The request body containing device details to be created.
     * @return The created Device object.
     * @throws ResourceAlreadyExists if a device with the same model already exists.
     * @throws ResourceUnableToCreate if there is an error while saving the device.
     */
    @PostMapping("/create") // Endpoint to create a new device
    @PreAuthorize("hasRole('admin')") // Restrict access to users with the 'admin' role
    public Device createDevice(@RequestBody CreateDeviceRequest request) throws ResourceAlreadyExists, ResourceUnableToCreate {
        // Convert CreateDeviceRequest to Device model
        Device device = request.toDevice();
        // Save the device using the service and return the created device
        return deviceService.saveDevice(device);
    }

    /**
     * Retrieves a device by its model.
     *
     * @param model The model of the device to retrieve.
     * @return ResponseEntity containing the found Device.
     * @throws ResourceNotFoundException if no device with the specified model is found.
     */
    @GetMapping("/get") // Endpoint to get a device by its model
    public ResponseEntity<Device> getDeviceByModel(@RequestParam("model") String model) throws ResourceNotFoundException {
    	if (model == null) { // Null check for model parameter
            throw new IllegalArgumentException("Model parameter cannot be null");
        }
        // Call the service method to retrieve the device and return the response
        return deviceService.getDeviceByModel(model);
    }

    /**
     * Retrieves all devices.
     *
     * @return ResponseEntity containing a list of all Device entities.
     */
    @GetMapping("/all") // Endpoint to retrieve all devices
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices(); // Get all devices from the service
        return ResponseEntity.ok(devices); // Returns a 200 OK response with the list of devices
    }
}
