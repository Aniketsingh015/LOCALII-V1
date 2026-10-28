package com.localii.backend.catalog.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.localii.backend.identity.entity.VendorProfile;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="vendor_id",nullable=false)
    private VendorProfile vendor;

    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="category_id",nullable=false)
    private Category category;

    @Column(name="name",nullable=false,length=200)
    private String name;

    @Column(name="description",nullable=false,length=1000)
    private String description;

    

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal price;

    @Column(nullable=false)
    private int stock=0;

     @Column(nullable = false)
    private boolean active = true;

    @Column(name = "removal_reason", length = 500)
    private String removalReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

  

    

}
