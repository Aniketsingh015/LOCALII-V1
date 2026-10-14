package com.localii.backend.identity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.localii.backend.identity.entity.VerificationStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDocumentRequest {
    @NotNull
    private VerificationStatus status;   // must be APPROVED or REJECTED

    @Size(max = 500)
    private String reason;               // required only when REJECTED

    @JsonIgnore
    @AssertTrue(message = "Status must be APPROVED or REJECTED")
    public boolean isDecisionValid() {
        return status == null || status != VerificationStatus.PENDING;
    }

    @JsonIgnore
    @AssertTrue(message = "Reason is required when rejecting")
    public boolean isReasonPresentWhenRejected() {
        return status != VerificationStatus.REJECTED || (reason != null && !reason.isBlank());
    }
}