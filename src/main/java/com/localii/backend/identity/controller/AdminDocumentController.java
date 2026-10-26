package com.localii.backend.identity.controller;

import com.localii.backend.identity.dto.DocumentResponse;
import com.localii.backend.identity.dto.ReviewDocumentRequest;
import com.localii.backend.identity.entity.VerificationStatus;
import com.localii.backend.identity.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/documents")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")//only an admin can list documents or review them. A vendor or customer gets 403 on every route in this class.
public class AdminDocumentController {

    private final DocumentService documentService;

    @GetMapping
    public List<DocumentResponse> list(@RequestParam(defaultValue = "PENDING") VerificationStatus status) {//Read a value from the URL’s query string, like ?status=APPROVED.
        return documentService.listByStatus(status).stream().map(DocumentResponse::new).toList();
    }

    @PatchMapping("/{id}")
    public DocumentResponse review(@PathVariable UUID id,
                                   Authentication auth,
                                   @Valid @RequestBody ReviewDocumentRequest request) {
        UUID adminId = UUID.fromString(auth.getName());
        return new DocumentResponse(documentService.review(id, adminId, request));
    }
}