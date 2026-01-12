# Hostel Issue Tracker – Backend

Spring Boot 3 REST API for managing hostel maintenance tickets with JWT-based authentication and role-based access control.

## 🧱 Tech Stack

- Java 17
- Spring Boot 3 (Web, Security, Data JPA)
- MySQL
- JWT authentication
- Maven

## 📁 Project Structure

- `src/main/java/com/sintu/issue_tracker` – main application code
  - `config` – security, CORS, application config
  - `controller` – REST controllers (auth, tickets, admin)
  - `dto` – request/response DTOs
  - `model` – JPA entities and enums
  - `repository` – Spring Data JPA repositories
  - `security` – JWT filter and service
  - `service` – business logic
- `src/main/resources/application.properties` – configuration (DB, JWT, etc.)

## ⚙️ Running Locally

### Prerequisites

- Java 17+
- Maven
- MySQL running locally

### Steps

```bash
# In backend root
mvn clean package

# Run the application
mvn spring-boot:run
