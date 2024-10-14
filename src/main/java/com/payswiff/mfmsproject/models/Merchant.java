package com.payswiff.mfmsproject.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a merchant in the system.
 * This class is mapped to the 'merchant' table in the database.
 */
@Entity
@Table(name = "merchant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchant {

    /**
     * The unique identifier for the merchant.
     * This is automatically generated and used as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private Integer merchantId;

    /**
     * The unique UUID of the merchant.
     * This is used to identify the merchant across different systems.
     */
    @Column(name = "merchant_uuid", nullable = false, unique = true, length = 36)
    private String merchantUuid;

    /**
     * The email address of the merchant.
     * This must be unique and is used for communication and identification.
     */
    @Column(name = "merchant_email", nullable = false, unique = true)
    private String merchantEmail;

    /**
     * The phone number of the merchant.
     * This must be unique and is used for communication purposes.
     */
    @Column(name = "merchant_phone", nullable = false, unique = true)
    private String merchantPhone;

    /**
     * The business name of the merchant.
     * This represents the name under which the merchant operates.
     */
    @Column(name = "merchant_business_name", nullable = false)
    private String merchantBusinessName;

    /**
     * The type of business the merchant is engaged in.
     * This can be used for categorization or reporting purposes.
     */
    @Column(name = "merchant_business_type", nullable = false)
    private String merchantBusinessType;
    
    /**
     * The timestamp when the merchant was created.
     * This is automatically set when the merchant is first created.
     */
    @Column(name = "merchant_creation_time")
    @CreationTimestamp
    private LocalDateTime merchantCreationTime;

    /**
     * The timestamp when the merchant was last updated.
     * This is automatically updated whenever the merchant's details are modified.
     */
    @UpdateTimestamp
    @Column(name = "merchant_updation_time")
    private LocalDateTime merchantUpdationTime;
}
