CREATE TABLE products (
    id              UUID PRIMARY KEY,
    vendor_id       UUID NOT NULL REFERENCES vendor_profiles(id),
    category_id     UUID NOT NULL REFERENCES categories(id),
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(1000) NOT NULL,
    price           NUMERIC(10,2) NOT NULL CHECK (price > 0),
    stock           INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    removal_reason  VARCHAR(500),
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_products_vendor ON products(vendor_id);
CREATE INDEX idx_products_category ON products(category_id);