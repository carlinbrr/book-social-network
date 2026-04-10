# Book Social Network 

![Github Release](https://img.shields.io/github/v/release/carlinbrr/book-social-network)

[![Deploy / Release](https://github.com/carlinbrr/book-social-network/actions/workflows/deploy-release.yml/badge.svg?branch=main)](https://github.com/carlinbrr/book-social-network/actions/workflows/deploy-release.yml)


A cloud platform simulating a social network for book sharing, built to demonstrate **modern software engineering practices** including scalable architecture, identity management, infrastructure as code, and CI/CD pipelines.

> The application idea and functional features were inspired by a public course.
> **System architecture, infrastructure, deployment strategy, and engineering practices have been designed and implemented specifically for this project.**


---

## 1. Key Highlights

- Fullstack architecture with clear separation of concerns
- Infrastructure as Code (IaC) using AWS
- Automated CI/CD pipelines with GitHub Actions
- Environment portability (development → production)
- Externalized Identity and Access Management (IAM) with Keycloak
- Versioned database migrations
- Production-oriented deployment workflow

---

## 2. Application Features

The application simulates a social platform centered around book sharing and community interaction.

Users can:

- Register and authenticate securely via an external identity provider
- Manage personal profiles
- Create and manage books
- Borrow books from other users
- Leave reviews
- Mark books as favorites and rate them

Functional features are minimal. Emphasis has been placed on **core interactions** and **clean development practices**.

---

## 3. Architecture Overview

Book Social Network follows a modular fullstack architecture composed of loosely coupled services with clearly separated responsibilities and well-defined runtime dependencies.

### 3.1 Basic Components

- **Frontend** — Angular application handling user interaction
- **Backend** — Spring Boot REST API handling business logic
- **IAM** — Keycloak-based IAM
- **Database** — PostgresSQL instance for application and identity data
- **Infrastructure** — AWS CDK building Stack templates

Full details on architecture, components and diagrams are available in: [architecture.md](docs/architecture.md)

---

## 4. CI/CD

The project uses GitHub Actions to automate validation and delivery workflows:

**Continuous Integration**
- Triggered on every push and pull request to `main`
- Builds and validates every developed module independently
- Ensures code quality and consistency before integration

**Continuous Delivery**
- Triggered on release/tag events
- Infrastructure is provisioned and updated via IaC
- Each module is built, versioned and deployed towards designed infrastructure

---

## 5. Deployment

The system is deployed on AWS following a secure and production-like architecture.

- Backend and Keycloak services run as containers behind load balancing
- Static frontend assets are distributed through a Content Delivery Network (CDN)
- Persistent data and file storage are managed independently from the application
- Configuration and secrets are managed securely
- Custom domain used, with TLS certificates ensuring secure communication

For detailed deployment strategy, see: [deployment.md](docs/deployment.md)

---

## 6. Local Development

The system can be run easily along its dependent services, such as databases and Keycloak which are provided via Docker Compose, making setup **reproducible for local development and future testing**.


For detailed setup instructions, see: [local-development.md](docs/local-development.md)

---

## 7. Project Evolution

The project has evolved through multiple functional and architectural stages:

- **v0.0.1-SNAPSHOT** - Local Foundations
- **v2.0.0** - Containerized Deployment
- **v3.0.0** - Environment Portability
- **v4.0.0** - Keycloak Migration
- **v5.0.0** - MVP Complete

For a complete release history, see: [CHANGELOG.md](CHANGELOG.md)

---

## 8. Project Structure

```
book-social-network/
├── .github/                      # GitHub workflows and CI/CD configuration
├── apps/                         # Product applications
│     ├── api/                    # Spring Boot backend service
│     └── web/                    # Angular frontend application
├── docs/                         # Architecture, deployment, ADRs, setup guides
├── infra/                        # IaC
│     └── cdk/                    # Java AWS CDK Project
├── platform/                     # Platform components
│     ├── database/               # Database initialization and migration scripts
│     └── iam/                    # IAM realm configuration and custom extension
└── docker-compose.yml            # Local infrastructure services
```

---

## 9. Purpose

This is a personal project that allows me to **apply my professional experience and knowledge** in software development, architecture, and DevOps practices.

The project is designed to **evolve over time**, serving as a platform to experiment and integrate new technologies and practices as I continue to grow as a Software Engineer.