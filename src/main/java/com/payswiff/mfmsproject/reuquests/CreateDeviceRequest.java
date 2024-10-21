package com.payswiff.mfmsproject.reuquests;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.payswiff.mfmsproject.models.Device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to create a new Device.
 * This class holds the necessary information to create a Device entity.
 */
@Data
@Builder
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateDeviceRequest {

    /**
     * The model of the device.
     */
    private String deviceModel;

    /**
     * The manufacturer of the device.
     */
    private String deviceManufacturer;

    /**
     * Converts this CreateDeviceRequest to a Device entity.
     *
     * @return A Device entity populated with the data from this request,
     *         including a randomly generated UUID for the device.
     */
    public Device toDevice() {
//        return Device.builder()
//                .deviceManufacturer(this.deviceManufacturer)
//                .deviceModel(this.deviceModel)
//                .deviceUuid(UUID.randomUUID().toString())
//                .build();
    	ModelMapper modelMapper = new ModelMapper();
        Device device = modelMapper.map(this, Device.class);
        device.setDeviceUuid(UUID.randomUUID().toString()); // Set UUID separately
        return device;
    }

	public CreateDeviceRequest(String deviceModel, String deviceManufacturer) {
		
		this.deviceModel = deviceModel;
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @param deviceManufacturer the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	
}
