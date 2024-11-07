package com.payswiff.mfmsproject.controllers;

import java.util.List; // Importing List for handling collections of Merchant entities.

import org.springframework.beans.factory.annotation.Autowired; // Importing annotation for dependency injection.
import org.springframework.http.HttpStatus; // Importing HTTP status codes for responses.
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for building HTTP responses.
import org.springframework.web.bind.annotation.*; // Importing Spring MVC annotations for RESTful web services.

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists; // Importing exception for handling already existing resources.
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException; // Importing exception for handling resource not found errors.
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate; // Importing exception for handling creation errors.
import com.payswiff.mfmsproject.models.Merchant; // Importing the Merchant model for handling merchant data.
import com.payswiff.mfmsproject.reuquests.CreateMerchantRequest; // Importing the request object for creating merchants.
import com.payswiff.mfmsproject.services.MerchantService; // Importing the service layer for business logic related to merchants.

/**
 * MerchantController handles requests related to merchant management,
 * including creating new merchants and retrieving existing merchants.
 */
@RestController
@RequestMapping("/api/merchants")
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.2.4:5173"})

public class MerchantController {

    @Autowired
    private MerchantService merchantService; // Injecting the MerchantService for business logic

    /**
     * Creates a new Merchant after checking for existing merchants with the same email or phone number.
     *
     * @param request The request containing merchant details.
     * @return ResponseEntity containing the created merchant.
     * @throws ResourceAlreadyExists if a merchant with the same email or phone already exists.
     * @throws ResourceUnableToCreate if there's an error while creating the merchant.
     */
    @PostMapping("/create")
    public ResponseEntity<Merchant> createMerchant(@RequestBody CreateMerchantRequest request) 
            throws ResourceAlreadyExists, ResourceUnableToCreate {
        
        // Attempt to create a new Merchant using the service and return the created Merchant
        Merchant createdMerchant = merchantService.createMerchant(request.toMerchant());
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED); // Return the created Merchant with HTTP 201 Created status
    }

    /**
     * Retrieves a Merchant by email or phone number.
     *
     * @param email The email of the merchant (optional).
     * @param phone The phone number of the merchant (optional).
     * @return ResponseEntity containing the found Merchant.
     * @throws ResourceNotFoundException if no merchant is found with the provided email or phone.
     */
    @GetMapping("/get")
    public ResponseEntity<Merchant> getMerchantByEmailOrPhone(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone) throws ResourceNotFoundException {
        
        // Fetch the Merchant based on email or phone number from the service
        Merchant merchant = merchantService.getMerchantByEmailOrPhone(email, phone);
        return new ResponseEntity<>(merchant, HttpStatus.FOUND); // Return the found Merchant with HTTP 302 Found status
    }

    /**
     * Retrieves all merchants.
     *
     * @return ResponseEntity containing a list of all Merchant entities.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        // Get all merchants from the service
        List<Merchant> merchants = merchantService.getAllMerchants();
        return ResponseEntity.ok(merchants); // Returns a 200 OK response with the list of merchants
    }
}
