package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDocumentRequest {
    @NotNull
    private DocumentType documentType;

    @NotBlank @Size(max = 1000)
    private String documentUrl;
}