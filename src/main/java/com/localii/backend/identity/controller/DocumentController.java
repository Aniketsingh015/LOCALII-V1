package com.localii.backend.identity.controller;

import com.localii.backend.identity.dto.CreateDocumentRequest;
import com.localii.backend.identity.dto.DocumentResponse;
import com.localii.backend.identity.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR','DELIVERY_PARTNER')")
    public ResponseEntity<DocumentResponse> create(Authentication auth,
                                                   @Valid @RequestBody CreateDocumentRequest request) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DocumentResponse(documentService.create(userId, request)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR','DELIVERY_PARTNER')")
    public List<DocumentResponse> listMine(Authentication auth) {
        UUID userId = UUID.fromString(auth.getName());
        return documentService.listMine(userId).stream().map(DocumentResponse::new).toList();
    }
}