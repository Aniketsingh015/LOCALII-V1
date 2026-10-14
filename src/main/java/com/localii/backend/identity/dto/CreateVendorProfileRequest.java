package com.localii.backend.identity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVendorProfileRequest {

    @NotBlank @Size(max = 255)
    private String shopName;

    @NotBlank @Size(max = 1000)
    private String description;

    @NotBlank @Size(max = 500)
    private String address;

    @NotBlank @Size(max = 100)
    private String city;

    @NotBlank @Size(max = 255)
    private String businessHours;
}