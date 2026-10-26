CREATE TABLE product_images (
    id             UUID PRIMARY KEY,
    product_id     UUID NOT NULL REFERENCES products(id),
    image_url      VARCHAR(1000) NOT NULL,
    display_order  INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX idx_product_images_product ON product_images(product_id);