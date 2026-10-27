package com.localii.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateCategoryRequest{
    @NotBlank @Size(max=200)
    private String name;
}