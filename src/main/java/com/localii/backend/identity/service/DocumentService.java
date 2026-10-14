package com.localii.backend.identity.service;

import com.localii.backend.common.exception.DuplicateResourceException;
import com.localii.backend.common.exception.ResourceNotFoundException;
import com.localii.backend.identity.dto.CreateDocumentRequest;
import com.localii.backend.identity.dto.ReviewDocumentRequest;
import com.localii.backend.identity.entity.User;
import com.localii.backend.identity.entity.VerificationDocument;
import com.localii.backend.identity.entity.VerificationStatus;
import com.localii.backend.identity.repository.DeliveryPartnerProfileRepository;
import com.localii.backend.identity.repository.UserRepository;
import com.localii.backend.identity.repository.VendorProfileRepository;
import com.localii.backend.identity.repository.VerificationDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final VerificationDocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final DeliveryPartnerProfileRepository deliveryProfileRepository;

    @Transactional
    public VerificationDocument create(UUID userId, CreateDocumentRequest request) {
        VerificationDocument doc = new VerificationDocument();
        doc.setUser(userRepository.getReferenceById(userId));
        doc.setDocumentType(request.getDocumentType());
        doc.setDocumentUrl(request.getDocumentUrl());
        return documentRepository.save(doc);
    }

    @Transactional(readOnly = true)
    public List<VerificationDocument> listMine(UUID userId) {
        return documentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<VerificationDocument> listByStatus(VerificationStatus status) {
        return documentRepository.findByStatusOrderByCreatedAtAsc(status);
    }

    @Transactional
    public VerificationDocument review(UUID documentId, UUID adminId, ReviewDocumentRequest request) {
        VerificationDocument doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        if (doc.getStatus() != VerificationStatus.PENDING) {
            throw new DuplicateResourceException("Document already reviewed");
        }

        doc.setStatus(request.getStatus());
        doc.setReviewedBy(userRepository.getReferenceById(adminId));

        if (request.getStatus() == VerificationStatus.REJECTED) {
            doc.setRejectionReason(request.getReason());
            return doc;
        }

        // APPROVED: activate the owner and approve their profile
        User owner = doc.getUser();
        owner.setStatus(User.Status.ACTIVE);

        if (owner.getRole() == User.Role.VENDOR) {
            vendorProfileRepository.findByUserId(owner.getId())
                    .ifPresent(p -> p.setVerificationStatus(VerificationStatus.APPROVED));
        } else if (owner.getRole() == User.Role.DELIVERY_PARTNER) {
            deliveryProfileRepository.findByUserId(owner.getId())
                    .ifPresent(p -> p.setVerificationStatus(VerificationStatus.APPROVED));
        }
        return doc;
    }
}