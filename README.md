# Library Management System

A RESTful Library Management System built with Spring Boot. The application provides CRUD operations for managing books, authors, and members with JWT-based authentication and role-based authorization.

## Technologies

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Gradle
- Lombok
- MapStruct
- Swagger/OpenAPI
- JUnit 5
- Mockito
- H2 Database (Testing)

## Features

- CRUD operations for Books, Authors, and Members
- DTO-based architecture
- Input validation
- Global exception handling
- Pagination and sorting
- API documentation with Swagger/OpenAPI
- User Registration
- User Login with JWT Authentication
- Stateless Authentication
- Role-based Authorization (USER / ADMIN)
- Protected API Endpoints
- Custom Authentication & Authorization Error Handling (401 / 403)
- JWT Token Expiration Validation
- Custom JPQL & Native SQL Queries
- Dynamic Filtering with JPA Specification
- Transaction Management (`@Transactional`)
- Unit Tests
- Integration Tests
- Rollback Transaction Tests

## Project Structure

```text
src
├── config
├── controller
├── exception
├── mapper
├── model
│   ├── dto
│   └── entity
├── repository
├── security
├── specification
├── service
└── test
```

## Getting Started

### Clone the repository

```bash
git clone https://github.com/zeyncoder/LibraryManagerSystem.git
```

### Configure the Database

Update the `application.yml` file:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/library
    username: postgres
    password: your_password

jwt:
  secret: your_secret_key
  expiration: 86400000
```

### Run the Application

```bash
./gradlew bootRun
```

Application URL:

```
http://localhost:8080
```

## API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

## Authentication

1. Register a new user using `/api/auth/register`
2. Login using `/api/auth/login`
3. Copy the returned JWT token.
4. Click **Authorize** in Swagger.
5. Enter:

```text
Bearer <your_token>
```

6. Access protected endpoints.

## Security

- JWT Authentication
- Stateless Session Management
- Role-based Authorization (USER / ADMIN)
- 401 Unauthorized handling
- 403 Forbidden handling
- JWT Expiration Validation

## Testing

The project includes:

- Unit Tests (Mockito)
- Integration Tests (Spring Boot + H2)
- Transaction Rollback Tests
- Repository Query Tests

Run all tests:

```bash
./gradlew test
```

## Author

**Zeynal Zeynalov**