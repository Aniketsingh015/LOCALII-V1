# Localii Backend

Hyperlocal multi-vendor quick-commerce platform — Java 21, Spring Boot 3, PostgreSQL, Redis.

## Folder structure

```
src/main/java/com/localii/backend/
├── identity/       # User, VendorProfile, DeliveryPartnerProfile, VerificationDocument
├── catalog/        # Product, ProductImage, Category
├── cart/           # Cart, CartItem
├── order/          # Order, OrderItem
├── payment/        # Payment (gateway integration)
├── delivery/       # Nearest-partner matching (Redis geospatial), delivery status
├── admin/          # Coupon, vendor/partner approval actions
├── notification/   # Notification
├── config/         # SecurityConfig, RedisConfig, OpenApiConfig
└── common/         # Shared exceptions, base entities, utils
```

Each module (once built out) will internally follow: `controller/`, `service/`, `repository/`, `entity/`, `dto/`.

## Running locally

1. Start Postgres and Redis:
   ```
   docker compose up -d
   ```

2. Run the app (from your IDE, or via terminal once Maven is installed locally):
   ```
   mvn spring-boot:run
   ```

3. The app runs on `http://localhost:8080`.

## Notes

- `spring.jpa.hibernate.ddl-auto` is set to `validate` — schema is managed entirely through Flyway migrations in `src/main/resources/db/migration`, not Hibernate auto-generation. This matches real production practice: the database schema is version-controlled, not inferred from your entity classes.
- JWT secret in `application.yml` is a placeholder — replace before any real deployment.
