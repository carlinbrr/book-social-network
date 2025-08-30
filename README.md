# Book Social Network (BSN)

## Description
BSN is a fullstack social network application created with Spring Boot + Spring Security/JWT and Angular + Bootstrap.  
It provides user interaction, posts, and manage the book status.  
The application is production-ready and supports Docker-based deployment, PostgreSQL, and Nginx.

---

## Technology Stack

- **Backend:** Spring Boot, Spring Security, JWT, Spring Data JPA, OpenAPI, Maven  
- **Frontend:** Angular, Bootstrap  
- **Database:** PostgreSQL  
- **Mail Server:** Gmail SMTP  
- **Containerization:** Docker & Docker Compose  
- **CI/CD:** GitHub Actions  
- **Deployment:** AWS EC2  

---

## Architecture


- Frontend is served by Nginx and loads configuration from `assets/environment.js` and `nginx.conf` mounted via Docker
- Backend reads configuration from an external Spring Boot file (`application-prod.yml`) mounted via Docker.  
- PostgreSQL and Mail server can run in separate containers for dev or prod environments.  
