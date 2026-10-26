package com.localii.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakedownRequest {

    @NotBlank @Size(max = 500)
    private String reason;
}