package com.localii.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = ".*[A-Za-z0-9].*", message = "Name must contain letters or digits")
    private String name;

    // optional: leave out for a top-level category
    private UUID parentId;
}