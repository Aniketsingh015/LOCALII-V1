package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDeliveryPartnerProfileRequest {
    @NotNull
    private VehicleType vehicleType;

    @NotBlank @Size(max = 50)
    private String vehicleNumber;
}