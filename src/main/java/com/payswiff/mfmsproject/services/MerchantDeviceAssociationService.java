package com.payswiff.mfmsproject.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.dtos.MerchantDeviceCountDTO;
import com.payswiff.mfmsproject.exceptions.ResourceNotFoundException;
import com.payswiff.mfmsproject.models.Device;
import com.payswiff.mfmsproject.models.Merchant;
import com.payswiff.mfmsproject.models.MerchantDeviceAssociation;
import com.payswiff.mfmsproject.repositories.DeviceRepository;
import com.payswiff.mfmsproject.repositories.MerchantDeviceAssociationRepository;
import com.payswiff.mfmsproject.repositories.MerchantRepository;

@Service
public class MerchantDeviceAssociationService {

    @Autowired
    private MerchantDeviceAssociationRepository associationRepository; // Repository for merchant-device associations

    @Autowired
    private MerchantRepository merchantRepository; // Repository to check if a Merchant exists

    @Autowired
    private DeviceRepository deviceRepository; // Repository to check if a Device exists

    /**
     * Associates a device with a merchant.
     *
     * @param merchantId The ID of the merchant.
     * @param deviceId   The ID of the device.
     * @return The created MerchantDeviceAssociation object.
     * @throws ResourceNotFoundException If the merchant or device is not found.
     */
    public MerchantDeviceAssociation assignDeviceToMerchant(Long merchantId, Long deviceId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Check if the device exists
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device", "ID", deviceId.toString()));

        // Create a new association between the merchant and the device
        MerchantDeviceAssociation association = new MerchantDeviceAssociation();
        association.setMerchant(merchant); // Set the associated merchant
        association.setDevice(device); // Set the associated device

        // Save the association to the repository and return the saved object
        return associationRepository.save(association);
    }
    
    /**
     * Retrieves a list of devices associated with a given merchant ID.
     *
     * @param merchantId The ID of the merchant.
     * @return A list of devices associated with the merchant.
     * @throws ResourceNotFoundException If the merchant is not found.
     */
    public List<Device> getDevicesByMerchantId(Long merchantId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Find associations for the merchant
        List<MerchantDeviceAssociation> associations = associationRepository.findAllByMerchant(merchant);

        // Extract devices from the associations and return them as a list
        return associations.stream()
                .map(MerchantDeviceAssociation::getDevice) // Map each association to its device
                .collect(Collectors.toList()); // Collect and return as a list
    }
    
    /**
     * Checks if a device is associated with a specific merchant.
     *
     * @param merchantId The ID of the merchant.
     * @param deviceId   The ID of the device.
     * @return true if the device is associated with the merchant, false otherwise.
     * @throws ResourceNotFoundException If the merchant or device is not found.
     */
    public boolean isDeviceAssociatedWithMerchant(Long merchantId, Long deviceId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Check if the device exists
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device", "ID", deviceId.toString()));

        // Check if the association between merchant and device exists
        return associationRepository.existsByMerchantAndDevice(merchant, device);
    }
    
    /**
     * Retrieves a count of devices associated with each merchant.
     *
     * @return A list of MerchantDeviceCountDTO containing merchant ID and device count.
     */
    public List<MerchantDeviceCountDTO> getDeviceCountByMerchant() {
        // Retrieve the count of devices associated with each merchant from the repository
        List<Object[]> results = associationRepository.countDevicesByMerchant();

        // Map the results to DTOs and collect them into a list
        return results.stream()
                .map(result -> new MerchantDeviceCountDTO(
                        (Long) result[0], // Merchant ID
                        (Long) result[1]  // Device count
                ))
                .collect(Collectors.toList());
    }
}
