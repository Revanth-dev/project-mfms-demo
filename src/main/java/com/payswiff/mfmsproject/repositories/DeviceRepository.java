package com.payswiff.mfmsproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payswiff.mfmsproject.models.Device;

/**
 * Repository interface for managing Device entities.
 * This interface provides methods to perform CRUD operations on the 'device' table.
 */
@Repository // Indicates that this interface is a Spring Data repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Finds a Device entity by its model.
     *
     * @param model The model of the device to search for.
     * @return The Device entity if found; otherwise, returns null.
     */
    @Query(value = "SELECT * FROM device WHERE device_model = :model", nativeQuery = true) // Custom SQL query to find a device by its model
    Device findByModel(@Param("model") String model); // Parameter binding for the SQL query

}
