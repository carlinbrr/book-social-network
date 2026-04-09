# Architecture

Book Social Network is structured as a modular system with clear boundaries between application, identity, data, and infrastructure layers.

The architecture promotes loose coupling and well-defined responsibilities, while operating as a cohesive platform where components interact through explicit runtime dependencies.

---

## 1. Approach

The system follows a layered approach:

- **Application layer** handles user interaction and business logic
- **Platform layer** provides shared capabilities such as identity and persistence
- **Infrastructure layer** defines how the system is provisioned, configured, and executed

This separation allows application logic, platform capabilities, and infrastructure concerns to evolve independently within their respective boundaries.

---

## 2. Core Components

### 2.1 Frontend

The frontend is responsible for user interaction and acts as the entry point to the system.

- Delegates authentication to the identity layer
- Communicates with backend services through defined APIs
- Remains independent from backend implementation details

---

### 2.2 Backend

The backend encapsulates the domain logic of the system.

- Exposes REST APIs
- Processes business rules
- Integrates with identity, acting as OAuth2 Resource Server, and persistence layer
- Remains stateless, delegating all state management externally

---

### 2.3 IAM Layer

Authentication is externalized to a dedicated identity component.

- Handles authentication and token issuance
- Provides identity context to the backend
- Includes a custom Service Provider Interface (SPI) to align identity data with the application domain

---

### 2.4 Data Layer

Persistent data is managed through a relational database with a migration-based approach.

- Schema evolves through versioned changes
- Data structure is controlled and reproducible
- Changes are treated as part of the system lifecycle

---

### 2.5 File Handling

User-generated content is treated as a separate concern from application logic.

- Files are not stored within application runtime
- Storage is handled independently from application services

---

## 3. Communication Model

The system follows a clear interaction model:

1. The frontend handles user interaction
2. Authentication is performed via the identity layer
3. The frontend communicates with backend APIs using authenticated context
4. The backend processes requests and interacts with persistent data

### 3.1 High-level System Diagram

```
                   +-->  [IAM]  <--+
                   |               |   
                   v               v
[User]  <-->  [Frontend]  -->  [Backend]  -->  [Database]
                                           |   
                                           +--> [Shared File Storage]
```

---

## 4. Design Principles

The system consistently follows these principles:

- **Separation of concerns** — each component has a clear responsibility
- **Modularity** — components evolve independently
- **Externalized identity** — removes authentication complexity from business logic
- **Stateless services** — enables horizontal scalability and simpler deployments
- **Controlled data evolution** — ensures consistency across environments through versioned changes