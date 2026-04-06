# Web

Frontend application responsible for user interaction and presentation, consuming backend APIs and handling authentication flows.

---

## 1. Responsibilities

- Provide user interface for core features (books, users, feedback)
- Interact with backend APIs for data and operations
- Manage client-side state and user interactions

---

## 2. Tech Stack

- Angular 20
- TypeScript
- HTML / SCSS
- Bootstrap 5
- OpenAPI Generator
- Keycloak JS

---

## 3. System Overview

### 3.1 Design
- Built using standalone Angular components, following a component-based architecture
- Uses Angular Router for structured navigation and route management
- Protects routes using guards integrated with the authentication flow
- Delegates authentication flows to external IAM (Keycloak)

### 3.2 Implementation Notes
- Uses OpenAPI Generator to produce API services and models
- Authentication flow handled through Keycloak JS
- Adds JWT tokens to outgoing requests via HTTP interceptor

### 3.3 Runtime & Configuration
- Uses environment-based configuration to adapt to different deployments
- Can be containerized using the provided Dockerfile and NGINX configuration

## 4. Local Development

The application depends on external services such as the backend API and IAM system to operate correctly.
It is not intended to run in isolation.

For detailed information, see [local-development.md](../../docs/local-development.md)
