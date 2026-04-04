# API

Book Social Network backend service, responsible for business logic, data access, and secure request processing within the platform.

---

## 1. Responsibilities

- Expose secured REST endpoints covering core domain features (users, books, feedback)
- Execute domain logic following clear separation between application and domain layers
- Handle data persistence and retrieval through structured data access patterns 

---

## 2. Tech Stack

- Java 21
- Spring Boot 4
- Spring Security
- JPA/Hibernate
- Flyway
- OpenAPI

---

## 3. System Overview

### 3.1 Design

- Follows Domain-Driven Design (DDD) principles
- Operates as an OAuth2 Resource Server, enforcing authentication and role-based authorization on endpoints

### 3.2 Implementation Notes
- Supports request validation and pagination at API level
- Implements data access using JPA, alongside HQL and Specifications
- Database schema validated by Hibernate, and managed via Flyway migrations (executed in local environments)
- Uses structured logging for observability

### 3.3 Runtime & Configuration
- Uses Maven and Spring profiles to manage build and runtime configuration (e.g. dev, cloud)
- Can be containerized using the provided Dockerfile for deployment scenarios

---

## 4. Local Development

This service depends on external components such us a PostgresSQL database and an IAM system to operate correctly.
It is not intended to run in isolation, but as part of the full system.

For detailed information, see [local-development.md](../../docs/local-development.md)