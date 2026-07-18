# Library Management System

A RESTful Library Management System built with Spring Boot. The application provides CRUD operations for managing books, authors, and members.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle
- Lombok
- MapStruct
- Swagger/OpenAPI

## Features

- CRUD operations for Books, Authors, and Members
- DTO-based architecture
- Input validation
- Global exception handling
- Pagination and sorting
- API documentation with Swagger/OpenAPI

## Project Structure

```
src
├── config
├── controller
├── exception
├── mapper
├── model
│   ├── dto
│   └── entity
├── repository
└── service
```

## Getting Started

### Clone the repository

```bash
git clone https://github.com/zeyncoder/LibraryManagerSystem.git
```

### Configure the Database

Update the `application.yml` file with your PostgreSQL configuration.

Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/library
    username: postgres
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Run the Application

```bash
./gradlew bootRun
```

The application will start at:

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

## Author

**Zeynal Zeynalov**
