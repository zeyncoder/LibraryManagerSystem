# Library Management System

A RESTful Library Management System built with Spring Boot. The application provides CRUD operations for managing books, authors, members, and categories with JWT-based authentication, role-based authorization, Redis caching, secure file management, scheduled tasks, asynchronous processing, transaction management, and comprehensive API documentation.

## Technologies

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* PostgreSQL
* H2 Database
* Gradle
* Lombok
* MapStruct
* Spring Cache
* Redis
* Swagger/OpenAPI
* JUnit 5
* Mockito

## Features

### Core Features

* CRUD operations for Books, Authors, Members, and Categories
* DTO-based architecture
* Input validation with Jakarta Validation
* Global exception handling
* Layered architecture
* Pagination and sorting
* Dynamic filtering with JPA Specification
* Custom JPQL queries
* Native SQL queries
* Transaction management with `@Transactional`
* Transaction rollback testing

### Authentication & Security

* User Registration
* User Login
* JWT Authentication
* Stateless Authentication
* Role-based Authorization (`USER` / `ADMIN`)
* Protected API endpoints
* JWT token expiration validation
* Custom `401 Unauthorized` handling
* Custom `403 Forbidden` handling
* Password hashing

### Testing

* Unit Tests with Mockito
* Integration Tests with Spring Boot and H2
* Repository Query Tests
* Transaction Rollback Tests

### Caching

* Spring Cache abstraction
* Redis support
* Caching for `getBookById`
* Caching for book list and search operations
* Caching for price, author, category, and dynamic filter queries
* Pageable-aware cache keys
* Cache invalidation after create/update/delete operations

### File Management

* Book cover upload
* Book cover download
* Multipart file handling
* File type validation
* File size validation
* Book existence validation
* Path traversal protection
* Safe normalized file paths
* Dynamic `Content-Type` detection during download

### Scheduled Processing

* Scheduled background tasks using `@Scheduled`
* Automatic cleanup of unused book cover files
* Daily scheduled cleanup process
* Logging and exception handling for scheduled tasks

### Asynchronous Processing

* Asynchronous processing using `@Async`
* Custom `ThreadPoolTaskExecutor`
* Configurable core pool size
* Configurable maximum pool size
* Configurable queue capacity
* Dedicated thread naming
* Non-blocking notification simulation

### Configuration

* Externalized application configuration
* Development and production profiles
* Environment variable support
* Externalized database configuration
* Externalized JWT configuration
* Separate Hibernate and logging settings for different environments

### API Documentation

* Swagger/OpenAPI integration
* Controller and endpoint documentation
* Request and response documentation
* Error response documentation
* Pagination and sorting documentation
* JWT Bearer authentication in Swagger

## Project Structure

```text
src
├── config
├── controller
├── exception
├── mapper
├── model
│   ├── dto
│   │   ├── request
│   │   └── response
│   └── entity
├── repository
├── security
├── specification
├── service
└── test
```

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/zeyncoder/LibraryManagerSystem.git
cd LibraryManagerSystem
```

### Configure Environment Variables

```bash
export DB_URL=jdbc:postgresql://localhost:5432/library
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export JWT_SECRET=your_secret_key
export JWT_EXPIRATION=86400000
```

### Development Profile

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Production Profile

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

### Run the Application

```bash
./gradlew bootRun
```

Application URL:

```text
http://localhost:8080
```

## API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

## Authentication

1. Register a new user using `/api/auth/register`.
2. Login using `/api/auth/login`.
3. Copy the returned JWT token.
4. Open Swagger UI.
5. Click **Authorize**.
6. Enter:

```text
Bearer <your_token>
```

7. Access protected endpoints according to the user's role.

## Main API Endpoints

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

### Books

```text
POST   /api/books
GET    /api/books
GET    /api/books/{id}
PUT    /api/books/{id}
DELETE /api/books/{id}

GET    /api/books/search
GET    /api/books/filter
GET    /api/books/price-range
GET    /api/books/author
GET    /api/books/category
GET    /api/books/jpql
GET    /api/books/native

POST   /api/books/{id}/cover
GET    /api/books/{id}/cover
```

### Authors

```text
POST   /api/authors
GET    /api/authors
GET    /api/authors/{id}
PUT    /api/authors/{id}
DELETE /api/authors/{id}
```

### Members

```text
POST   /api/members
GET    /api/members
GET    /api/members/{id}
PUT    /api/members/{id}
DELETE /api/members/{id}
```

### Categories

```text
POST /api/categories
GET  /api/categories
```

## Caching

The application uses Spring Cache abstraction with Redis to improve the performance of read-oriented operations.

Cached operations include:

* Individual book retrieval
* Book list retrieval
* Book title search
* Price range queries
* Author-based queries
* Category-based queries
* Price comparison queries
* Dynamic book filtering

Cache keys include relevant query parameters and pagination/sorting information to prevent different requests from sharing incorrect cached results.

Cache entries are automatically invalidated when books are created, updated, or deleted.

## File Management

Book covers can be uploaded and downloaded through dedicated endpoints.

Supported features:

* Multipart file upload
* File type validation
* File size validation
* Book existence validation
* Secure file path resolution
* Path traversal protection
* Dynamic download `Content-Type`

Allowed upload types:

```text
image/jpeg
image/png
```

Maximum file size:

```text
5 MB
```

## Scheduled Processing

The application uses Spring's `@Scheduled` annotation for background tasks.

A scheduled cleanup task runs daily and removes unused book cover files that are no longer referenced by books in the database.

```text
Database cover references
          ↓
Upload directory scan
          ↓
Unused files detected
          ↓
Unused files deleted
```

## Asynchronous Processing

The application uses `@Async` for non-blocking background processing.

Notifications are processed using a custom `ThreadPoolTaskExecutor`.

Example:

```text
Book operation
      ↓
Async notification
      ↓
ThreadPoolTaskExecutor
      ↓
Notification processing
```

## Security

* JWT Authentication
* Stateless Session Management
* Role-based Authorization
* Password Hashing
* JWT Expiration Validation
* 401 Unauthorized handling
* 403 Forbidden handling
* Secure file path validation

## Configuration Profiles

The application uses environment-specific configuration files:

```text
application.yml
application-dev.yml
application-prod.yml
```

Development and production environments use separate configuration settings.

Sensitive values are provided through environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

Example development configuration:

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/library_db}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
```

Example production configuration:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

Production uses environment variables instead of hard-coded credentials.

## Testing

The project includes:

* Unit Tests with Mockito
* Integration Tests with Spring Boot and H2
* Repository Query Tests
* Transaction Rollback Tests

Run all tests:

```bash
./gradlew test
```

## Build

Build the project:

```bash
./gradlew clean build
```

Run the generated JAR:

```bash
java -jar build/libs/*.jar
```

## Development Notes

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

DTOs are used for API requests and responses, while MapStruct is used for entity/DTO mapping.

The project also includes reusable specifications for dynamic book filtering and separate configuration for caching, scheduling, asynchronous processing, security, and application profiles.

## Author

**Zeynal Zeynalov**
