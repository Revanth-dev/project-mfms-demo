package com.payswiff.mfmsproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Merchant;
import com.payswiff.mfmsproject.reuquests.CreateMerchantRequest;
import com.payswiff.mfmsproject.services.MerchantService;

@RestController
@RequestMapping("/api/merchants")
@CrossOrigin(origins = "http://localhost:5173") // Allow specific origin

public class MerchantController {

    @Autowired
    private MerchantService merchantService;

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
        
        Merchant createdMerchant = merchantService.createMerchant(request.toMerchant());
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<Merchant> getMerchantByEmailOrPhone(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone) throws ResourceNotFoundException {
        
        Merchant merchant = merchantService.getMerchantByEmailOrPhone(email, phone);
        return new ResponseEntity<>(merchant, HttpStatus.FOUND);
    }
    /**
     * Retrieves all merchants.
     *
     * @return ResponseEntity containing a list of all Merchant entities.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        List<Merchant> merchants = merchantService.getAllMerchants();
        return ResponseEntity.ok(merchants); // Returns a 200 OK response with the list of merchants
    }
}
