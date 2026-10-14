CREATE TABLE vendor_profiles (
    id                  UUID PRIMARY KEY,
    user_id             UUID NOT NULL UNIQUE REFERENCES users(id),
    shop_name           VARCHAR(255) NOT NULL,
    description         VARCHAR(1000) NOT NULL,
    address             VARCHAR(500) NOT NULL,
    city                VARCHAR(100) NOT NULL,
    business_hours      VARCHAR(255) NOT NULL,
    verification_status VARCHAR(255) NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_vendor_profiles_city ON vendor_profiles(city);