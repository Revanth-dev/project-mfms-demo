package com.payswiff.mfmsproject.reuquests;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payswiff.mfmsproject.models.Merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;


/**
 * Represents a request to create a new Merchant.
 * This class holds the necessary information to create a Merchant entity.
 */
@Data
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
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
//        return Merchant.builder()
//                .merchantUuid(UUID.randomUUID().toString())
//                .merchantBusinessName(merchantBusinessName)
//                .merchantBusinessType(merchantBusinessType)
//                .merchantEmail(merchantEmail)
//                .merchantPhone(merchantPhone)
//                .build();
    	ModelMapper modelMapper = new ModelMapper();
        Merchant merchant = modelMapper.map(this, Merchant.class);
        
        merchant.setMerchantUuid(UUID.randomUUID().toString()); // Set UUID separately
        return merchant;
    }

	/**
	 * @return the merchantEmail
	 */
	public String getMerchantEmail() {
		return merchantEmail;
	}

	/**
	 * @param merchantEmail the merchantEmail to set
	 */
	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	/**
	 * @return the merchantPhone
	 */
	public String getMerchantPhone() {
		return merchantPhone;
	}

	/**
	 * @param merchantPhone the merchantPhone to set
	 */
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}

	/**
	 * @return the merchantBusinessName
	 */
	public String getMerchantBusinessName() {
		return merchantBusinessName;
	}

	/**
	 * @param merchantBusinessName the merchantBusinessName to set
	 */
	public void setMerchantBusinessName(String merchantBusinessName) {
		this.merchantBusinessName = merchantBusinessName;
	}

	/**
	 * @return the merchantBusinessType
	 */
	public String getMerchantBusinessType() {
		return merchantBusinessType;
	}

	/**
	 * @param merchantBusinessType the merchantBusinessType to set
	 */
	public void setMerchantBusinessType(String merchantBusinessType) {
		this.merchantBusinessType = merchantBusinessType;
	}

	/**
	 * @param merchantEmail
	 * @param merchantPhone
	 * @param merchantBusinessName
	 * @param merchantBusinessType
	 */
	public CreateMerchantRequest(String merchantEmail, String merchantPhone, String merchantBusinessName,
			String merchantBusinessType) {
		this.merchantEmail = merchantEmail;
		this.merchantPhone = merchantPhone;
		this.merchantBusinessName = merchantBusinessName;
		this.merchantBusinessType = merchantBusinessType;
	}

	/**
	 * 
	 */
	public CreateMerchantRequest() {
	}
    
}
