package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.DeliveryPartnerProfile;
import com.localii.backend.identity.entity.VehicleType;
import com.localii.backend.identity.entity.VerificationStatus;
import lombok.Getter;
import java.util.UUID;

@Getter
public class DeliveryPartnerProfileResponse {
    private final UUID id;
    private final VehicleType vehicleType;
    private final String vehicleNumber;
    private final VerificationStatus verificationStatus;
    private final boolean available;

    public DeliveryPartnerProfileResponse(DeliveryPartnerProfile p) {
        this.id = p.getId();
        this.vehicleType = p.getVehicleType();
        this.vehicleNumber = p.getVehicleNumber();
        this.verificationStatus = p.getVerificationStatus();
        this.available = p.isAvailable();
    }
}