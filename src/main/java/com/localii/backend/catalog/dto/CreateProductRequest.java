package com.localii.backend.catalog.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter

public class CreateProductRequest {
    
    @NotBlank @Size(max=200)
    private String name;

    @NotBlank @Size(max=1000)
    private String description;

    @NotNull @DecimalMin("0.01") @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull @Min(value = 0) 
    private Integer stock;

    @NotNull 
    private UUID categoryId;
}
