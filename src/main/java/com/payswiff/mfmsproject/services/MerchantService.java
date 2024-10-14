package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.exceptions.ResourceAlreadyExists;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.exceptions.ResourceUnableToCreate;
import com.payswiff.mfmsproject.models.Merchant;
import com.payswiff.mfmsproject.repositories.MerchantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    /**
     * Creates a new merchant if no existing merchant with the same email or phone exists.
     *
     * @param merchant The Merchant entity to be created.
     * @return The created Merchant object.
     * @throws ResourceAlreadyExists if a merchant with the same email or phone number exists.
     * @throws ResourceUnableToCreate if any error occurs during the merchant creation process.
     */
    public Merchant createMerchant(Merchant merchant) throws ResourceAlreadyExists, ResourceUnableToCreate {
        try {
            // Check if merchant with the same email or phone already exists
            Optional<Merchant> existingMerchant = Optional.ofNullable(merchantRepository.findByMerchantEmailOrMerchantPhone(
                merchant.getMerchantEmail(), merchant.getMerchantPhone()));

            if (existingMerchant.isPresent()) {
                throw new ResourceAlreadyExists("Merchant", "Email/Phone", 
                        merchant.getMerchantEmail() + "/" + merchant.getMerchantPhone());
            }

            // Save the new merchant
            return merchantRepository.save(merchant);

        } catch (Exception e) {
            throw new ResourceUnableToCreate("Merchant", "Email/Phone", 
                    merchant.getMerchantEmail() + "/" + merchant.getMerchantPhone());
        }
    }
    /**
     * Retrieves a Merchant by its email or phone.
     *
     * @param email The email of the merchant to retrieve.
     * @param phone The phone number of the merchant to retrieve.
     * @return The found Merchant object.
     * @throws ResourceNotFoundException If no merchant is found with the given email or phone.
     */
    public Merchant getMerchantByEmailOrPhone(String email, String phone) throws ResourceNotFoundException {
        Optional<Merchant> optionalMerchant = Optional.ofNullable(
            merchantRepository.findByMerchantEmailOrMerchantPhone(email, phone));

        return optionalMerchant.orElseThrow(() -> 
            new ResourceNotFoundException("Merchant", "Email/Phone", email != null ? email : phone));
    }
    
    /**
     * Retrieves all merchants from the database.
     *
     * @return List of all Merchant entities.
     */
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll(); // Returns all merchants from the repository
    }
    /**
     * Checks if a merchant exists by its ID.
     *
     * @param merchantId The ID of the merchant.
     * @return true if a merchant with the given ID exists, false otherwise.
     */
    public boolean existsById(Long merchantId) {
        return merchantRepository.existsById(merchantId);  // Uses repository to check if merchant exists
    }
}
