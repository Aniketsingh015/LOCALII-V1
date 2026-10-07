package com.localii.backend.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final Instant timestamp;
    private final Map<String, String> fieldErrors;

    public ErrorResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    public ErrorResponse(HttpStatus status, String message, Map<String, String> fieldErrors) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = Instant.now();
        this.fieldErrors = fieldErrors;
    }
}