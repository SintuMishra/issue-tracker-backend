# ⚙️ Hostel Issue Tracker – Backend API

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Render](https://img.shields.io/badge/Live-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)](https://render.com/)

This is the robust REST API powering the Hostel Issue Tracker. It handles secure user authentication, ticket lifecycle management, and role-based access control (RBAC).

## 🚀 Live API Endpoint
**URL:** `https://issue-tracker-backend-1-86vi.onrender.com`

---

## 🔐 Security Architecture
The API uses **Spring Security** with **JWT (JSON Web Tokens)** for stateless authentication.



1. **Authentication:** Users log in via `/api/auth/login` to receive a JWT.
2. **Authorization:** The JWT must be included in the `Authorization: Bearer <token>` header for all protected routes.
3. **RBAC:** Endpoints are restricted based on roles (`ROLE_STUDENT`, `ROLE_ADMIN`).

---

## 🏗️ Technical Highlights

- **Custom CORS Configuration:** Seamlessly integrated with the Vercel-hosted frontend to allow secure cross-origin requests.
- **DTO Pattern:** Implemented Data Transfer Objects to decouple the database layer from the REST layer, improving security and performance.
- **Global Exception Handling:** Centralized error management to provide consistent API responses.
- **JPA/Hibernate:** Advanced mapping for complex relationships between Users and Tickets.

---

## 📂 Project Structure

```text
src/main/java/com/sintu/issue_tracker
├── config/      # Security, CORS, and App configurations
├── controller/  # REST Endpoints (Auth, Tickets, Admin)
├── dto/         # Request and Response payloads
├── model/       # JPA Entities (User, Ticket, Enums)
├── repository/  # Spring Data JPA Interfaces
├── security/    # JWT Filter, Token Provider, UserDetails
└── service/     # Core Business Logic

## 🚦 API Endpoints (Quick Reference)

### 🔐 Authentication
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user (Student/Admin) |
| `POST` | `/api/auth/login` | Authenticate and receive a JWT token |

### 🎫 Ticket Management
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/tickets` | Student/Admin | Fetch tickets related to the authenticated user |
| `POST` | `/api/tickets` | Student | Create a new maintenance request |
| `PUT` | `/api/tickets/{id}` | Admin | Update status (OPEN, RESOLVED) or assign staff |

---

## 🛠️ Local Development

### 1. Clone the repository
```bash
git clone [https://github.com/yourusername/issue-tracker-backend.git](https://github.com/yourusername/issue-tracker-backend.git)
cd issue-tracker-backend

erDiagram
    USER ||--o{ TICKET : creates
    USER {
        long id PK
        string username
        string password
        string role "STUDENT / ADMIN / STAFF"
    }
    TICKET {
        long id PK
        string title
        string description
        string status "OPEN / IN_PROGRESS / RESOLVED"
        string priority "LOW / MEDIUM / HIGH"
        string category
        long user_id FK "Created By"
        long assigned_to_id FK "Staff Member"
    }

    Table,Primary Key,Key Fields,Relationships
users,id,"username, role",1:N with tickets
tickets,id,"title, status, priority",FK to users (creator_id)

### 🔧 Database Configuration (Local)
To run this project locally, update `src/main/resources/application.properties` with your local PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/issuetracker
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE