CREATE TABLE verification_documents (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    document_type VARCHAR(255) NOT NULL,
    document_url VARCHAR(1000) NOT NULL,
    status VARCHAR(255) NOT NULL,
    rejection_reason VARCHAR(500),
    reviewed_by UUID REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_verification_documents_user ON verification_documents(user_id);
CREATE INDEX idx_verification_documents_status ON verification_documents(status);