# Vehicle Service

The **Vehicle Service** is a standalone Spring Boot application that provides a REST API to retrieve information about
registered vehicles by their registration number. It acts as an integration layer, designed to be consumed by other
services (e.g., an insurance system).

## Features

- Exposes a REST endpoint to query vehicle data by registration number.
- Returns vehicle details like make, model, and year.
- Returns `404` if the vehicle is not found.
- Documented with Swagger / OpenAPI 3.
- Ready for integration via RESTful interfaces.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- Git (for cloning the repo)

### Clone the repository

```
git clone https://github.com/your-org/vehicle-service.git
cd vehicle-service
````

### Run Tests

This will run all unit, integration, and E2E tests.

```
mvn clean verify
```

### Run the Application

Start the Spring Boot application locally:

```
mvn spring-boot:run
```

The application will start on port **8080** by default but it can be changed in the application.yml

## API Documentation

Once running, the OpenAPI/Swagger documentation is available at:

* Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* OpenAPI spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## API Usage

### GET `/api/v1/vehicle/{registrationNumber}`

**Description**: Returns vehicle information if the registration number is valid and exists in the system.

**Sample Request**:

```http
GET /api/v1/vehicle/ABC123 HTTP/1.1
Host: localhost:8080
```

**Successful Response** (`200 OK`):

```json
{
  "registrationNumber": "ABC123",
  "make": "Toyota",
  "model": "Camry",
  "year": 2018
}
```

**Validation Errors** (`400 Bad Request`):

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid registration number"
}
```

**Not Found** (`404 Not Found`):

```
Vehicle not found
```

## File Structure (Simplified)

```
vehicle-service/
├── src/
│   ├── main/
│   │   └── java/dev/modig/vehicle/
│   │       ├── controller/         # REST controller
│   │       ├── service/            # Business logic
│   │       ├── repository/         # In-memory data
│   │       ├── model/              # Vehicle data model
│   │       ├── exception/          # Custom exceptions
│   └── test/                       # Unit, integration, E2E tests
├── pom.xml
└── README.md
```

## Build Package

To create a deployable JAR:

```
mvn clean package
```

The output JAR will be in `target/vehicle-service-*.jar`.

## Configuration

Application configuration is located at:

```
src/main/resources/application.yml
```

Default port: `8080`
You can change it with:

```
server:
  port: 8090
```

## CI/CD Integration

This service is compatible with GitHub Actions and includes a basic pipeline that:

* Runs tests on PRs and pushes.
* Deploys to production when merging into `main`. (Note: this step is currently commented out and not tested.)

See `.github/workflows/ci-cd.yml` for pipeline details.

## Improvement points

* The vehicle data is hardcoded. In a real application, it should be stored in a database.
* No authentication or authorization is implemented.
* Input validation and sanitization are minimal.
* Contract testing (e.g., using Pact) could be added.
* Logging is minimal and could be improved for better traceability.
* The deployment step in GitHub Actions is commented out and untested—it may contain errors.
* Spring Boot profiles could be introduced to support multiple environments (e.g., dev, test, prod).

## PLEASE NOTE

General design discussions and personal reflections about the solution and the assignment is located in the README.md
for the insurance-service.