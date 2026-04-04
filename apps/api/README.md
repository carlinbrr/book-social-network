# API

Backend service responsible for business logic, data access, and secure request processing within the platform.

---

## 1. Responsibilities

- Expose REST APIs
- Process domain logic
- Interact with persistent data
- Enforce authentication and authorization

---

## 2. Tech Stack

- Java 21
- Spring Boot 4
- Spring Security
- Hibernate
- PostgreSQL
- OpenAPI

---

## 3. Architecture

- Follows **Domain-Driven Design (DDD)**
- Stateless service
- Acts as an **OAuth2 Resource Server**
- API documented via **OpenAPI**

---

## 4. Local Development

This service depends on external components such us a PostgresSQL database and identity provider to operate correctly.
It is not intended to run in isolation, but as part of the full system.

For detailed information, see [local-development.md](../../docs/local-development.md)