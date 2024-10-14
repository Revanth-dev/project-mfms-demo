package com.payswiff.mfmsproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private MerchantDeviceAssociationRepository associationRepository;

    @Autowired
    private MerchantRepository merchantRepository; // Repository to check if Merchant exists

    @Autowired
    private DeviceRepository deviceRepository; // Repository to check if Device exists

    /**
     * Associates a device with a merchant.
     *
     * @param merchantId The ID of the merchant.
     * @param deviceId   The ID of the device.
     * @return The created association.
     * @throws ResourceNotFoundException If the merchant or device is not found.
     */
    public MerchantDeviceAssociation assignDeviceToMerchant(Long merchantId, Long deviceId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Check if the device exists
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device", "ID", deviceId.toString()));

        // Create the association
        MerchantDeviceAssociation association = new MerchantDeviceAssociation();
        association.setMerchant(merchant); // Assuming there's a getMerchantId method
        association.setDevice(device); // Assuming there's a getDeviceId method

        // Save the association
        return associationRepository.save(association);
    }
}
