package com.localii.backend.catalog.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UpdateStockRequest {
    @NotNull @Min(value=0)
    private Integer stock;
}
