package com.payswiff.mfmsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payswiff.mfmsproject.models.MerchantDeviceAssociation;

/**
 * Repository interface for managing {@link MerchantDeviceAssociation} entities.
 * This interface extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface MerchantDeviceAssociationRepository extends JpaRepository<MerchantDeviceAssociation, Integer> {
    
    /**
     * You can define additional query methods here if needed.
     * For example, methods to find associations by merchant or device.
     */
}
