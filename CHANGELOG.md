# Changelog

All notable changes to this project are documented in this file.

This project follows a structured release process where each version reflects architectural evolution and improvements in engineering practices.

---

## [v5.0.0 - MVP Complete](https://github.com/carlinbrr/book-social-network/releases/tag/v5.0.0)

### Added

- MVP functionality completed, enabling full end-to-end application usage
- Book details and waiting list features

### Improved

- Core application modules stabilized
- UI and backend reliability improved
- Mobile navigation and responsiveness fixed

### Fixed

- Multiple critical issues across frontend and backend

---

## [v4.0.0 - Keycloak Migration](https://github.com/carlinbrr/book-social-network/releases/tag/v4.0.0)

### Added

- Integration with external Identity and Access Management (Keycloak)
- Custom Keycloak SPI for user synchronization
- Keycloak included in both local and production environments

### Changed

- Authentication and authorization migrated from custom JWT implementation to OAuth2 / OpenID Connect
- Backend redesigned as OAuth2 Resource Server
- Frontend integrated with Keycloak via official adapter

### Improved

- Security model aligned with industry standards
- Separation between identity and application logic

---

## [v3.0.0 - Environment Portability](https://github.com/carlinbrr/book-social-network/releases/tag/v3.0.0)

### Added

- Environment-aware configuration (local and production)
- Versioned Docker images aligned with release tags

### Changed

- CI/CD pipeline redesigned to trigger only on release/tag events
- Project structure adapted for environment portability

### Improved

- Deployment consistency across environments
- Reproducibility of application builds

---

## [v2.0.0 - Containerized Deployment](https://github.com/carlinbrr/book-social-network/releases/tag/v2.0.0)

### Added

- Containerization of backend and frontend services
- Docker Compose orchestration for local and EC2 environments
- Initial CI/CD pipeline for automated build and deployment

### Changed

- Transition from manual setup to container-based execution

### Improved

- Deployment consistency and developer onboarding

---

## [v0.0.1-SNAPSHOT - Local Foundations](https://github.com/carlinbrr/book-social-network/releases/tag/v0.0.1-SNAPSHOT)

### Added

- Initial backend implementation running locally
- Custom JWT-based authentication using Spring Security
- REST API for books and feedback with OpenAPI documentation
- Angular frontend connected via generated API client