package com.payswiff.mfmsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.reuquests.CreateDeviceRequest;
import com.payswiff.mfmsproject.services.DeviceService;

@RestController
@RequestMapping("/api/devices") // Base URL for all device-related APIs
@CrossOrigin(origins = "http://localhost:5173") // Allow specific origin

public class DeviceController {

    @Autowired
    private DeviceService deviceService; // Injecting the DeviceService to handle device-related operations

    /**
     * Creates a new device.
     * 
     * @param request The request body containing device details to be created.
     * @return The created Device object.
     * @throws ResourceAlreadyExists if a device with the same model already exists.
     * @throws ResourceUnableToCreate if there is an error while saving the device.
     */
    @PostMapping("/create") // Endpoint to create a new device
    @PreAuthorize("hasRole('admin')")
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
        // Call the service method to retrieve the device and return the response
        return deviceService.getDeviceByModel(model);
    }
    /**
     * Retrieves all devices.
     *
     * @return ResponseEntity containing a list of all Device entities.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices); // Returns a 200 OK response with the list of devices
    }
}
