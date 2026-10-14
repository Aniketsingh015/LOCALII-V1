package com.localii.backend.identity.dto;

import com.localii.backend.identity.entity.DocumentType;
import com.localii.backend.identity.entity.VerificationDocument;
import com.localii.backend.identity.entity.VerificationStatus;
import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

@Getter
public class DocumentResponse {
    private final UUID id;
    private final UUID userId;
    private final DocumentType documentType;
    private final String documentUrl;
    private final VerificationStatus status;
    private final String rejectionReason;
    private final Instant createdAt;

    public DocumentResponse(VerificationDocument d) {
        this.id = d.getId();
        this.userId = d.getUser().getId();
        this.documentType = d.getDocumentType();
        this.documentUrl = d.getDocumentUrl();
        this.status = d.getStatus();
        this.rejectionReason = d.getRejectionReason();
        this.createdAt = d.getCreatedAt();
    }
}