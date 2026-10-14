CREATE TABLE delivery_partner_profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE REFERENCES users(id),
    vehicle_type VARCHAR(255) NOT NULL,
    vehicle_number VARCHAR(50) NOT NULL,
    verification_status VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);