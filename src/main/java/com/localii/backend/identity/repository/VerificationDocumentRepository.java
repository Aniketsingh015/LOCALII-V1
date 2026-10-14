package com.localii.backend.identity.repository;

import com.localii.backend.identity.entity.VerificationDocument;
import com.localii.backend.identity.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface VerificationDocumentRepository extends JpaRepository<VerificationDocument, UUID> {
    List<VerificationDocument> findByUserIdOrderByCreatedAtDesc(UUID userId);
    List<VerificationDocument> findByStatusOrderByCreatedAtAsc(VerificationStatus status);
}