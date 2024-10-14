package com.payswiff.mfmsproject.reuquests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to associate a device with a merchant.
 * This class holds the necessary information required for the association.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDeviceAssociationRequest {
    
    /**
     * The ID of the merchant to which the device will be associated.
     */
    private Long merchantId; // ID of the merchant

    /**
     * The ID of the device to be associated with the merchant.
     */
    private Long deviceId;    // ID of the device
}
