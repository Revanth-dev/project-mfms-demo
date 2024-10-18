package com.payswiff.mfmsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.dtos.MerchantDeviceCountDTO;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.models.MerchantDeviceAssociation;
import com.payswiff.mfmsproject.reuquests.MerchantDeviceAssociationRequest;
import com.payswiff.mfmsproject.services.MerchantDeviceAssociationService;

@RestController
@RequestMapping("/api/MerchantDeviceAssociation")
@CrossOrigin(origins = "http://localhost:5173") // Allow specific origin

public class MerchantDeviceAssociationController {

	@Autowired
	private MerchantDeviceAssociationService associationService;

	/**
	 * Assigns a device to a merchant.
	 *
	 * @param request The request containing merchant and device IDs.
	 * @return ResponseEntity containing the created association.
	 * @throws ResourceNotFoundException
	 */
	@PostMapping("/assign")
	public ResponseEntity<MerchantDeviceAssociation> assignDeviceToMerchant(
			@RequestBody MerchantDeviceAssociationRequest request) throws ResourceNotFoundException {
		MerchantDeviceAssociation createdAssociation = associationService
				.assignDeviceToMerchant(request.getMerchantId(), request.getDeviceId());
		return new ResponseEntity<>(createdAssociation, HttpStatus.CREATED);
	}
	
	@GetMapping("/get/merchantdeviceslist")
	public ResponseEntity<List<Device>> getMerchantDevicesList(@RequestParam Long merchantId) throws ResourceNotFoundException {
	    List<Device> devices = associationService.getDevicesByMerchantId(merchantId);
	    return new ResponseEntity<>(devices, HttpStatus.OK);
	}
	
	@GetMapping("/check/merchant-device")
	public ResponseEntity<Boolean> checkMerchantDeviceAssociation(
	        @RequestParam Long merchantId,
	        @RequestParam Long deviceId) throws ResourceNotFoundException {
	    boolean exists = associationService.isDeviceAssociatedWithMerchant(merchantId, deviceId);
	    return new ResponseEntity<>(exists, HttpStatus.OK);
	}
	
	@GetMapping("/device-count")
    public ResponseEntity<List<MerchantDeviceCountDTO>> getDeviceCountByMerchant() {
        List<MerchantDeviceCountDTO> deviceCounts = associationService.getDeviceCountByMerchant();

       

        return new ResponseEntity<>(deviceCounts, HttpStatus.OK);
    }

}
