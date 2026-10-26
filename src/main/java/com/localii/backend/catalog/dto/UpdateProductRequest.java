package com.localii.backend.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductRequest {

    @NotBlank @Size(max = 255)
    private String name;

    @NotBlank @Size(max = 1000)
    private String description;

    @NotNull @DecimalMin("0.01") @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    private UUID categoryId;

    // null = keep the current images; a list = replace them
    @Size(max = 10)
    private List<@NotBlank @Size(max = 1000) String> imageUrls;
}