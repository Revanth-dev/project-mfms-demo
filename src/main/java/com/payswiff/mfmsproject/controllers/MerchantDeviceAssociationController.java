package com.payswiff.mfmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.MerchantDeviceAssociation;
import com.payswiff.mfmsproject.reuquests.MerchantDeviceAssociationRequest;
import com.payswiff.mfmsproject.services.MerchantDeviceAssociationService;

@RestController
@RequestMapping("/api/MerchantDeviceAssociation")
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
}
