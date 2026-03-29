# 📚 Book Social Network (BSN)

A fullstack platform simulating a social network for book sharing, designed to demonstrate modern software engineering practices including identity management, scalable services, containerized infrastructure, and automated deployment workflows.

This project emphasizes **engineering quality over feature quantity**, showing how systems can be structured, developed, secured, and deployed professionally.

---

## ⭐ Application Features

The application simulates a social platform centered around book sharing and community interaction.

Users can:

- Create and manage books
- Borrow books from other users
- Leave reviews
- Mark books as favorites and rate them
- Manage personal profiles
- Authenticate securely via an external identity provider

Functional features are intentionally minimal. The focus is on **core interactions** and **clean development practices**.

---

## 🏗️ Architecture Overview

Book Social Network follows a modular fullstack architecture composed of independent services with clearly separated responsibilities.

### System Components

- **Frontend** — Angular application handling user interaction.
- **Backend** — Spring Boot REST API handling business logic and persistence.
- **Identity Provider** — Keycloak-based identity and access management.
- **Databases** — PostgreSQL instances for application and identity data.
- **Custom Identity Extension** — Keycloak SPI for user synchronization.

### Architectural Principles

- Separation of concerns
- Domain-Driven Design (DDD) in backend services
- Externalized identity management
- OAuth2-based security model
- Container-first infrastructure
- Environment portability (development → production)
- Secure service-to-service communication

Full details on architecture, authentication flows, and deployment diagrams are available in:

```
docs/architecture.md
```

---

## ⚙️ Technology Stack

### Backend

- Java 21
- Spring Boot 3
- Spring Security (OAuth2 Resource Server)
- JPA / Hibernate
- Domain-Driven Design (DDD)
- OpenAPI documentation
- PostgreSQL

The backend exposes a REST API responsible for business logic and database interaction, acting as an OAuth2 Resource Server validating JWT tokens issued by Keycloak.

### Frontend

- Angular 20 (Standalone Components)
- Bootstrap 5
- Reactive architecture
- Modern Angular best practices
- Keycloak JS adapter for authentication integration

The frontend communicates with backend services while delegating authentication to Keycloak.

### Identity & Security

- Keycloak 26 (Identity and Access Management)
- OAuth2 / OpenID Connect
- JWT-based authentication
- Externalized authentication and authorization
- Custom Keycloak Service Provider Interface (SPI) developed with Java 21

The custom SPI ensures users in Keycloak remain synchronized with the backend domain model.

---

## 🚀 Development & Environment Strategy

The system can be run consistently across environments using Docker containers. Infrastructure services such as databases and Keycloak are provided via Docker Compose, making setup **reproducible for local development and future testing**.

For detailed setup instructions, see:

```
docs/local-development.md
```

---

## 🔄 CI/CD Pipeline

The project uses **GitHub Actions** to automate build, test, and deployment workflows.

- Each **GitHub release/tag triggers the CI/CD pipeline**.
- Pipeline tasks include:
    - Building backend and frontend services
    - Running automated tests (Yet to be included)
    - Building and publishing Docker images
    - Deploying services to AWS infrastructure
    - Managing environment configuration securely via GitHub Secrets

This guarantees reproducible builds, consistent deployments, and a reliable delivery workflow.

---

## 🚀 Deployment

All services — backend, frontend, Keycloak, databases, and supporting infrastructure — run on a single **AWS EC2 instance** with Docker Compose.

Deployment characteristics:

- Container-based service execution
- Environment portability and isolated configuration
- Secure integration of all components
- Adaptable to other infrastructures (e.g., ECS, RDS, S3) for future releases

For detailed deployment instructions, see:

```
docs/deployment.md
```

---

## 📦 Releases & Project Evolution

The project has evolved through multiple architectural stages:

- **v0.0.1-SNAPSHOT** — Initial local backend with JWT authentication
- **v2.0** — Containerized infrastructure using Docker and CI/CD workflows
- **v3.0** — Development and production-ready environment configuration
- **v4.0** — Migration to external identity management with Keycloak
- **v5.0** — Minimum Product Value (MVP) functionalities completed

For a complete release history, see:

```
CHANGELOG.md
```

---

## 📂 Project Structure

```
book-social-network/
├── .github/                      # GitHub workflows and CI/CD configuration
├── apps/                         # Product applications
│     ├── api/                    # Spring Boot backend service
│     └── web/                    # Angular frontend application
├── docs/                         # Architecture, deployment, ADRs, setup guides
├── infra/                        # Infrastucture as Code (IaC)
│     └── cdk/                    # Java AWS CDK Project
├── platform/                     # Platform components
│     ├── database/               # Database configuration
│     │     ├── init/             # Initialization SQL scripts
│     │     └── migrations/       # Migration SQL scripts
│     └── iam/                    # Identity and Access Management (IAM)
│           ├── keycloak-spi/     # Custom Keycloak extension
│           └── realms/           # Base Keycloak realm configuration
└── docker-compose.yml            # Local infrastructure services
```

---

## 📖 Documentation

Additional documentation is available under `docs/`:

- System architecture (`architecture.md`)
- Architecture decisions and rationale (`decisions.md`)
- Deployment and production strategy (`deployment.md`)
- Local development setup (`local-development.md`)

For detailed information on releases:

- Project evolution and releases (`CHANGELOG.md`)

---

## 🎯 Project Purpose & Personal Approach

This is a personal project that allows me to **apply my professional experience and knowledge** in software development, architecture, and DevOps practices.

The project emphasizes **high-quality architecture, secure integration, and reliable infrastructure**, while maintaining minimal functional features. It is designed to **evolve over time**, serving as a platform to experiment, learn, and integrate new technologies and best practices as I continue to grow as a Software Engineer.