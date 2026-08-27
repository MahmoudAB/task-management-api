# Task Management API

A simple REST API for creating, viewing, updating, and completing tasks. The project is built with Spring Boot, Spring MVC, and Spring Data JPA.

## Features

- Create a task
- Retrieve a task by ID
- List all tasks
- Update a task title
- Toggle a task between completed and incomplete
- Return `404 Not Found` when a task does not exist
- Unit and web-layer tests with JUnit 5, Mockito, and MockMvc
- JaCoCo test coverage reports

## Tech stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- H2 Database
- PostgreSQL driver
- Gradle
- JUnit 5 and Mockito

## Prerequisites

- Java 21 or newer

The Gradle wrapper is included, so a separate Gradle installation is not required.

## Run locally

Clone the repository, move into the project directory, and start the application:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

The API starts at `http://localhost:8080` by default. With the current configuration, Spring Boot automatically uses the included H2 database for local development.

## API endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/tasks/add` | Create a task |
| `GET` | `/tasks/{id}` | Retrieve a task by ID |
| `GET` | `/tasks/all` | Retrieve all tasks |
| `PUT` | `/tasks/{id}` | Update a task title |
| `PATCH` | `/tasks/{id}/toggle-completed` | Toggle the completion status |

### Create a task

```bash
curl -X POST http://localhost:8080/tasks/add \
  -H 'Content-Type: application/json' \
  -d '{"title":"Write project documentation"}'
```

Example response:

```json
{
  "id": 1,
  "title": "Write project documentation",
  "completed": false
}
```

### Retrieve a task

```bash
curl http://localhost:8080/tasks/1
```

### Retrieve all tasks

```bash
curl http://localhost:8080/tasks/all
```

### Update a task

```bash
curl -X PUT http://localhost:8080/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Publish project documentation"}'
```

### Toggle completion

```bash
curl -X PATCH http://localhost:8080/tasks/1/toggle-completed
```

Calling this endpoint changes `completed` from `false` to `true`, or from `true` to `false`.

### Error response

Requests for a missing task return HTTP `404 Not Found` with a plain-text message:

```text
Task with id 99 not found
```

## Testing

Run the test suite:

```bash
./gradlew test
```

The test task also generates a JaCoCo coverage report. Open the HTML report at:

```text
build/reports/jacoco/test/html/index.html
```

## Build

Create an executable application archive:

```bash
./gradlew build
```

Run the generated JAR:

```bash
java -jar build/libs/task-management-api-0.0.1-SNAPSHOT.jar
```

## Project structure

```text
src/
├── main/
│   ├── java/com/mahmoud/task_management_api/
│   │   ├── controller/   # REST endpoints
│   │   ├── dto/          # Request objects
│   │   ├── exception/    # API error handling
│   │   ├── model/        # JPA entities
│   │   ├── repository/   # Data access
│   │   └── service/      # Business logic
│   └── resources/        # Application configuration
└── test/                 # Controller and service tests
```
