# 0001. Externalized Identity Management

## Status

Accepted

## Context

Authentication was initially implemented within the backend using a JWT-based approach, creating custom filters in Spring Security.

This approach tightly coupled identity concerns with business logic, making the backend responsible for authentication, token validation, and role handling.

While suitable for a simpler stage, this design limited flexibility and did not align with a system designed with future growth in mind.

At the same time, the application requires its own user representation for domain-specific operations, creating a need to align identity with application data.

## Decision

Delegate authentication and authorization to an external Identity and Access Management (IAM) system.

The backend acts as an OAuth2 Resource Server, validating tokens and enforcing access based on authenticated context.

A custom Service Provider Interface (SPI) is introduced to enable controlled synchronization between the identity system and the application domain model.

## Consequences

- Identity management is fully decoupled from business logic
- Authentication flows follow standardized protocols (OAuth2 / OpenID Connect)
- Backend services focus on domain behavior and authorization decisions
- The system is better prepared for future growth and evolving security requirements
- Controlled integration between IAM and application data through a defined extension point (SPI)
- Introduces dependency on an external identity system
- Requires explicit handling of user synchronization between systems
- Adds architectural complexity and integration overhead