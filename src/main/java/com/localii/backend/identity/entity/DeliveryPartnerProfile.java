package com.localii.backend.identity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery_partner_profiles")
@Getter @Setter @NoArgsConstructor
public class DeliveryPartnerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "vehicle_number", nullable = false, length = 50)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus;

    // field is "available", not "isAvailable": Lombok would generate isAvailable()/setAvailable(),
    // and Jackson/JavaBeans naming then disagrees with the field name.
    @Column(name = "is_available", nullable = false)
    private boolean available = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}