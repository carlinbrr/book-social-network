# CDK

Infrastructure as Code module responsible for defining and synthesizing the AWS infrastructure of Book Social Network.

---

## 1. Responsibilities

- Define the cloud (AWS) infrastructure required by the platform
- Structure infrastructure into modular stacks aligned with system components
- Generate CloudFormation templates for reproducible deployments

---

## 2. Tech Stack

- Java 21
- AWS Cloud Development Kit (CDK) 

---

## 3. Implementation Notes

- The infrastructure is organized into the following stacks:
  - **Network** — networking, subnets, and security group boundaries
  - **Database** — relational database and database secrets
  - **FileSystem** — shared filesystem for persistent file storage
  - **Compute** — ECS cluster and runtime foundation
  - **Migration** — task definition for database initialization and migrations
  - **Services** — backend and IAM services, load balancing, and service wiring
  - **Frontend** — static frontend delivery and distribution
  - **DNS** — domain routing for API, IAM, and frontend endpoints
- Synthesizes infrastructure templates into /cdk.out
- Configuration is externalized via environment variables
- Can be packaged as an executable JAR