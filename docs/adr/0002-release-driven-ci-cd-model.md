# 0002. Release-Driven CI/CD Model

## Status

Accepted

## Context

Build, validation, and deployment were initially part of a more tightly coupled process, where changes could be promoted without a clearly defined release boundary.

As the system evolved to include multiple coordinated components (infrastructure, backend services, identity, frontend), deployments required a more structured approach to avoid partial updates and inconsistent system states.

A clear separation between validation and deployment was needed to improve reliability and control.

## Decision

Adopt a release-driven delivery model with a clear separation between Continuous Integration (CI) and Continuous Delivery (CD).

- CI runs on push and pull request events to validate changes early
- CD is triggered exclusively on release/tag events and acts as the single entry point for deployment

Deployment is executed as a system-level operation, where infrastructure and application components are updated together through a unified process.

## Consequences

- Establishes a clear boundary between code validation and system release
- Ensures that only versioned and intentional changes are deployed
- Enables full-system deployment instead of independent component updates
- Reduces the risk of inconsistent or partially updated environments
- Improves traceability between releases and deployed system state
- Requires disciplined release management practices
- Introduces a more structured workflow compared to simple push-based deployments
- Couples deployment to release cycles rather than individual changes