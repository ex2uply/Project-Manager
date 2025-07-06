# Project Management Spring Boot Application

## Table of Contents

* Introduction
* Functionalities
* Technologies Used
* Project Structure
* Security Implementation
* Lessons Learned

## Introduction

 A **project management backend** built using **Spring Boot** with a microservices architecture. It allows users to register, create projects, manage members, create tasks, assign them, and collaborate via comments.

## Functionalities

* **User Auth (JWT)** – Register/login with secure access
* **Project Creation** – Users can create and lead projects
* **Project Members** – Add team members with role-based access
* **Task Management** – Create and assign tasks inside projects
* **Task Progress** – Track task status (e.g., completed)
* **Comments** – Comment on both tasks and projects

## Technologies Used

| Tech                  | Purpose                        |
| --------------------- | ------------------------------ |
| Spring Boot           | Base framework                 |
| Spring Security + JWT | Auth and authorization         |
| Spring Data JPA       | DB persistence                 |
| MySQL                 | Main database                  |
| Eureka                | Service discovery              |
| Zuul Gateway          | Routing and API filtering      |
| Feign Client          | Internal service communication |
| Config Server         | Centralized config management  |
| Shared Lib            | Common DTOs, Enums, Exceptions |

## Project Structure

```
devvault/
├── api-gateway/
├── service-registry/
├── authentication-service/
├── project-service/
├── task-service/
├── comment-service/
├── config-server/
└── shared-lib/
```

## Security Implementation

1. All requests go through the API Gateway
2. Gateway checks token using Authentication Service
3. On successful auth, JWT is attached and request forwarded to service
4. All service endpoints are secured and role-checked internally

## Lessons Learned

* Built full microservice architecture from scratch
* Handled inter-service communication via Feign
* Implemented service discovery and routing
* Gained hands-on with token-based security (JWT)
* Clean separation of services for scalability and modularity
