# 0003. Infrastructure as Code Migration

## Status

Accepted

## Context

The system was initially deployed on a single host using a container-based setup, where all components were executed together.

While suitable for early development, this model concentrated multiple responsibilities in a single runtime environment, limiting scalability, reducing isolation between components, and making environment setup harder to reproduce reliably.

As the system grew, infrastructure needed to become a first-class concern, capable of evolving alongside the application.

## Decision
Adopt a cloud-based deployment model where infrastructure is defined and managed through Infrastructure as Code.

Infrastructure is treated as part of the system definition, allowing provisioning, configuration, and updates to be executed through version-controlled artifacts.

## Consequences

- Infrastructure becomes reproducible, versioned, and aligned with the application lifecycle
- Environments can be created, updated, or rebuilt consistently
- System components gain clearer runtime boundaries and isolation
- Changes to infrastructure are traceable and reviewable
- The system can scale and evolve without relying on manual setup or implicit knowledge
- Increases the complexity of the system by introducing infrastructure code
- Requires understanding of infrastructure design and dependencies
- Adds responsibility for maintaining and evolving infrastructure definitions