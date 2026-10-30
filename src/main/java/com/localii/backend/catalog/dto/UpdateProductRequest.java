package com.localii.backend.catalog.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateProductRequest {
    @NotBlank @Size(max=200)
    private String name;

    @NotBlank @Size(max=1000)
    private String description;

    @NotNull @DecimalMin("0.01") @Digits(integer = 8, fraction = 2)
    private BigDecimal price;


    @NotNull 
    private UUID categoryId;
}
