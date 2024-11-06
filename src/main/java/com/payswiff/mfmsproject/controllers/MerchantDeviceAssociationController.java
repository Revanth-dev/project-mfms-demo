package com.payswiff.mfmsproject.controllers;

import java.util.List; // Importing List for handling collections of devices.

import org.springframework.beans.factory.annotation.Autowired; // Importing annotation for dependency injection.
import org.springframework.http.HttpStatus; // Importing HTTP status codes for responses.
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for building HTTP responses.
import org.springframework.web.bind.annotation.*; // Importing Spring MVC annotations for RESTful web services.

import com.payswiff.mfmsproject.dtos.MerchantDeviceCountDTO; // Importing DTO for device counts by merchant.
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing exception for handling resource not found errors.
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Device; // Importing Device model for handling device data.
import com.payswiff.mfmsproject.models.MerchantDeviceAssociation; // Importing model for merchant-device associations.
import com.payswiff.mfmsproject.reuquests.MerchantDeviceAssociationRequest; // Importing request object for merchant-device associations.
import com.payswiff.mfmsproject.services.MerchantDeviceAssociationService; // Importing service layer for merchant-device association logic.

/**
 * MerchantDeviceAssociationController handles requests related to the 
 * association between merchants and devices, including assigning devices 
 * to merchants and checking existing associations.
 */
@RestController
@RequestMapping("/api/MerchantDeviceAssociation")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from this origin
public class MerchantDeviceAssociationController {

    @Autowired
    private MerchantDeviceAssociationService associationService; // Injecting the MerchantDeviceAssociationService for business logic

    /**
     * Assigns a device to a merchant.
     *
     * @param request The request containing merchant and device IDs.
     * @return ResponseEntity containing the created association.
     * @throws ResourceNotFoundException if the merchant or device is not found.
     * @throws ResourceUnableToCreate 
     */
    @PostMapping("/assign")
    public ResponseEntity<MerchantDeviceAssociation> assignDeviceToMerchant(
            @RequestBody MerchantDeviceAssociationRequest request) throws ResourceNotFoundException, ResourceUnableToCreate {
    	//check null
    	if(request==null) {
    		throw new ResourceUnableToCreate("NULL request for devicemerchnat association", null, null);
    	}
        // Create the association using the service layer
        MerchantDeviceAssociation createdAssociation = associationService
                .assignDeviceToMerchant(request.getMerchantId(), request.getDeviceId());
        return new ResponseEntity<>(createdAssociation, HttpStatus.CREATED); // Return the created association with HTTP 201 Created status
    }
    
    /**
     * Retrieves the list of devices associated with a specific merchant.
     *
     * @param merchantId The ID of the merchant whose devices are to be retrieved.
     * @return ResponseEntity containing a list of associated devices.
     * @throws ResourceNotFoundException if the merchant is not found.
     * @throws ResourceUnableToCreate 
     */
    @GetMapping("/get/merchantdeviceslist")
    public ResponseEntity<List<Device>> getMerchantDevicesList(@RequestParam Long merchantId) throws ResourceNotFoundException, ResourceUnableToCreate {
        //check null
    	if(merchantId==null) {
    		throw new ResourceNotFoundException("Merchnat ", "ID", null);
    	}
    	// Get the list of devices associated with the specified merchant
        List<Device> devices = associationService.getDevicesByMerchantId(merchantId);
        return new ResponseEntity<>(devices, HttpStatus.OK); // Return the list of devices with HTTP 200 OK status
    }
    
    /**
     * Checks if a device is associated with a specific merchant.
     *
     * @param merchantId The ID of the merchant.
     * @param deviceId   The ID of the device.
     * @return ResponseEntity containing a boolean indicating the association status.
     * @throws ResourceNotFoundException if the merchant or device is not found.
     * @throws ResourceUnableToCreate 
     */
    @GetMapping("/check/merchant-device")
    public ResponseEntity<Boolean> checkMerchantDeviceAssociation(
            @RequestParam Long merchantId,
            @RequestParam Long deviceId) throws ResourceNotFoundException, ResourceUnableToCreate {
    	//check null
    	 if (merchantId == null || deviceId == null) {
    	        throw new ResourceNotFoundException("merchantDevice", "merchnatId or DeviceId", null); // Return 400 if any ID is null
    	    }
        // Check if the specified device is associated with the specified merchant
        boolean exists = associationService.isDeviceAssociatedWithMerchant(merchantId, deviceId);
        return new ResponseEntity<>(exists, HttpStatus.OK); // Return the association status with HTTP 200 OK status
    }
    
    /**
     * Retrieves the count of devices associated with each merchant.
     *
     * @return ResponseEntity containing a list of device counts by merchant.
     */
    @GetMapping("/device-count")
    public ResponseEntity<List<MerchantDeviceCountDTO>> getDeviceCountByMerchant() {
        // Get the counts of devices associated with each merchant
        List<MerchantDeviceCountDTO> deviceCounts = associationService.getDeviceCountByMerchant();
        return new ResponseEntity<>(deviceCounts, HttpStatus.OK); // Return the list of device counts with HTTP 200 OK status
    }
}
