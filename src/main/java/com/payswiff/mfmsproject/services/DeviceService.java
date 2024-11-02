package com.payswiff.mfmsproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.repositories.DeviceRepository;


@Service
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;

	/**
	 * Saves a new device if no existing device with the same model already exists.
	 *
	 * @param device The Device entity to be saved. Must not be null and the device model 
	 *               must not be empty.
	 * @return The saved Device object.
	 * @throws ResourceAlreadyExists if a device with the same model already exists.
	 * @throws ResourceUnableToCreate if the device is null, if the device model is empty, 
	 *                                 or if any error occurs during the device saving process.
	 */
	public Device saveDevice(Device device) throws ResourceAlreadyExists, ResourceUnableToCreate {
	    // Validate that the device is not null and the device model is not empty
	    if (device == null || 
	        (device.getDeviceModel() == null || device.getDeviceModel().isEmpty())) {
	        throw new ResourceUnableToCreate("Device", "Model", 
	                "Device cannot be null and the device model cannot be empty.");
	    }

	    // Check if a device with the same model already exists
	    Optional<Device> existingDevice = Optional.ofNullable(deviceRepository.findByModel(device.getDeviceModel()));

	    // If a device with the same model is found, throw a ResourceAlreadyExists exception
	    if (existingDevice.isPresent()) {
	        throw new ResourceAlreadyExists("Device", "Model", device.getDeviceModel());
	    }

	    try {
	        // Try to save the new device and return it
	        return deviceRepository.save(device);
	    } catch (Exception e) {
	        // If any exception occurs during the save process, throw ResourceUnableToCreate
	        throw new ResourceUnableToCreate("Device", "Model", device.getDeviceModel());
	    }
	}

	/**
	 * Retrieves a Device by its model.
	 * 
	 * @param model The model of the device to retrieve.
	 * @return The ResponseEntity containing the found Device.
	 * @throws ResourceNotFoundException If no device with the specified model is found.
	 */
	public ResponseEntity<Device> getDeviceByModel(String model) throws ResourceNotFoundException {
	    // Use Optional to handle the possibility of the device not being found
	    Optional<Device> optionalDevice = Optional.ofNullable(deviceRepository.findByModel(model));

	    // If present, return the device, otherwise throw the ResourceNotFoundException
	    Device device = optionalDevice
	        .orElseThrow(() -> new ResourceNotFoundException("Device", "Model", model));

	    return new ResponseEntity<>(device, HttpStatus.FOUND);
	}

	/**
     * Retrieves all devices from the database.
     *
     * @return List of all Device entities.
     */
    public List<Device> getAllDevices() {
        return deviceRepository.findAll(); // Returns all devices from the repository
    }
    /**
     * Checks if a device exists by its ID.
     *
     * @param deviceId The ID of the device.
     * @return true if a device with the given ID exists, false otherwise.
     */
    public boolean existsById(Long deviceId) {
        return deviceId != null && deviceRepository.existsById(deviceId); // Use repository to check if device exists
    }

	
   
}
