# HMCTS Task Manager Backend

The backend component of the HMCTS Task Manager application, built with Spring Boot and PostgreSQL.

## Technology Stack

- **Spring Boot 3**: Application framework
- **PostgreSQL**: Database
- **Flyway**: Database migrations
- **SpringDoc OpenAPI**: API documentation
- **Lombok**: Boilerplate code reduction
- **JUnit 5**: Testing framework
- **Testcontainers**: Integration testing with containers
- **Docker**: Required for Testcontainers

## Features

- RESTful API for task management
- Data validation
- Error handling
- Database migrations with Flyway
- Comprehensive test coverage
- Swagger UI documentation

## Getting Started

### Prerequisites

- Java 17 or higher
- PostgreSQL 14 or higher
- Gradle

### Database Setup

1. Create a PostgreSQL database:

```sql
CREATE DATABASE hmcts_task;
CREATE USER postgres WITH PASSWORD 'test123';
GRANT ALL PRIVILEGES ON DATABASE hmcts_task TO postgres;
```

Or adjust the `application.properties` file to match your existing PostgreSQL configuration.

### Installation

1. Clone the repository
2. Navigate to the backend directory:

```bash
cd hmcts-backend
```

3. Run the application:

```bash
# Using Gradle wrapper
./gradlew bootRun

# Or with Gradle if installed
gradle bootRun
```

The application will start on port 8080.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:

http://localhost:8080/swagger-ui.html

This provides an overview of all available endpoints with the ability to test them directly.

## API Endpoints

| HTTP Method | Endpoint | Description |
|-------------|----------|-------------|
| GET | /api/tasks | Get all tasks |
| GET | /api/tasks/{id} | Get a specific task |
| POST | /api/tasks | Create a new task |
| PUT | /api/tasks/{id} | Update a task |
| PATCH | /api/tasks/{id}/status | Update a task's status |
| DELETE | /api/tasks/{id} | Delete a task |

## Project Structure

```
hmcts-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── scot/oskar/hmcts/backend/
│   │   │       ├── config/           # Configuration classes
│   │   │       ├── controller/       # REST controllers
│   │   │       ├── data/             # Data models and DTOs
│   │   │       ├── exception/        # Exception handling
│   │   │       ├── repository/       # Data repositories
│   │   │       ├── service/          # Business logic
│   │   │       └── HMCTSBackendApplication.java
│   │   └── resources/
│   │       ├── db/migration/         # Flyway migration scripts
│   │       └── application.properties # Application configuration
│   └── test/
│       └── java/                     # Test classes
├── build.gradle                      # Gradle build configuration
└── gradlew                           # Gradle wrapper
```

## Testing

To run tests:

```bash
./gradlew test
```

This will run both unit and integration tests. The integration tests use Testcontainers to spin up a PostgreSQL container, so Docker must be running on your machine.

## Development

For local development:

1. Ensure PostgreSQL is running
2. Configure `application.properties` to point to your local database
3. Run the application with `./gradlew bootRun`
4. Make changes and the application will hot-reload

## Building for Production

To build a production JAR:

```bash
./gradlew build
```

The resulting JAR will be in `build/libs/` directory.