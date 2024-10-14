package com.payswiff.mfmsproject.reuquests;

import java.util.UUID;

import com.payswiff.mfmsproject.models.Merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to create a new Merchant.
 * This class holds the necessary information to create a Merchant entity.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMerchantRequest {

    /**
     * The email address of the merchant.
     */
    private String merchantEmail;

    /**
     * The phone number of the merchant.
     */
    private String merchantPhone;

    /**
     * The name of the merchant's business.
     */
    private String merchantBusinessName;

    /**
     * The type of the merchant's business.
     */
    private String merchantBusinessType;

    /**
     * Converts this CreateMerchantRequest to a Merchant entity.
     *
     * @return A new Merchant entity populated with the data from this request,
     *         including a randomly generated UUID for the merchant.
     */
    public Merchant toMerchant() {
        return Merchant.builder()
                .merchantUuid(UUID.randomUUID().toString())
                .merchantBusinessName(merchantBusinessName)
                .merchantBusinessType(merchantBusinessType)
                .merchantEmail(merchantEmail)
                .merchantPhone(merchantPhone)
                .build();
    }
}
