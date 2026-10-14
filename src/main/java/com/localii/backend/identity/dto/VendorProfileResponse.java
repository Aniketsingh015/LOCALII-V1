package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.VendorProfile;
import com.localii.backend.identity.entity.VerificationStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VendorProfileResponse {

    private final UUID id;
    private final String shopName;
    private final String description;
    private final String address;
    private final String city;
    private final String businessHours;
    private final VerificationStatus verificationStatus;

    public VendorProfileResponse(VendorProfile profile) {
        this.id = profile.getId();
        this.shopName = profile.getShopName();
        this.description = profile.getDescription();
        this.address = profile.getAddress();
        this.city = profile.getCity();
        this.businessHours = profile.getBusinessHours();
        this.verificationStatus = profile.getVerificationStatus();
    }
}