# IAM

Platform module responsible for identity and access management configuration, providing authentication, authorization, and identity integration across the system.

---

## 1. Responsibilities

- Define and manage realm configuration
- Provide authentication and authorization capabilities
- Externalize identity concerns from the application services
- Ensure consistent and reproducible IAM setup across environments

---

## 2. Structure

```
iam/
├── keycloak-spi/        # Custom extension for identity synchronization
├── realms/              # Base realm configuration
└── Dockerfile           # Container definition for execution
```

---

## 3. Implementation Notes

- Uses a predefined realm file as a baseline configuration (clients, roles, authentication flows...)
- Supports environment-specific customization through external configuration
- Extends IAM behavior through a custom Service Interface Provider (SPI) to synchronize identity data with the application domain