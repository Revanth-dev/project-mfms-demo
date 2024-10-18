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
    
    public List<Device> getDevicesByMerchantId(Long merchantId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Find associations for the merchant
        List<MerchantDeviceAssociation> associations = associationRepository.findAllByMerchant(merchant);

        // Extract devices from associations
        return associations.stream()
                .map(MerchantDeviceAssociation::getDevice)
                .collect(Collectors.toList());
    }
    
    public boolean isDeviceAssociatedWithMerchant(Long merchantId, Long deviceId) throws ResourceNotFoundException {
        // Check if the merchant exists
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "ID", merchantId.toString()));

        // Check if the device exists
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device", "ID", deviceId.toString()));

        // Check if the association exists
        return associationRepository.existsByMerchantAndDevice(merchant, device);
    }
    
    public List<MerchantDeviceCountDTO> getDeviceCountByMerchant() {
        List<Object[]> results = associationRepository.countDevicesByMerchant();
        return results.stream()
                .map(result -> new MerchantDeviceCountDTO(
                        (Long) result[0],
                        (Long) result[1]
                ))
                .collect(Collectors.toList());
    }
}
