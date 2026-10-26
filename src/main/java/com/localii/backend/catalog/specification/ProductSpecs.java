package com.localii.backend.catalog.specification;

import com.localii.backend.catalog.entity.Product;
import com.localii.backend.identity.entity.VerificationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class ProductSpecs {

    private ProductSpecs() {}

    // only active products of approved shops are visible to the public
    public static Specification<Product> visible() {
        return (root, query, cb) -> cb.and(
                cb.isTrue(root.<Boolean>get("active")),
                cb.equal(root.join("vendor").get("verificationStatus"), VerificationStatus.APPROVED));
    }

    public static Specification<Product> inCity(String city) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.join("vendor").<String>get("city")), city.toLowerCase());
    }

    public static Specification<Product> inCategory(UUID categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> nameContains(String q) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.<String>get("name")), "%" + q.toLowerCase() + "%");
    }
}