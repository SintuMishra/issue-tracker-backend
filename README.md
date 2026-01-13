⚙️ Hostel Issue Tracker – Backend APIThis is the robust REST API powering the Hostel Issue Tracker. It handles secure user authentication, ticket lifecycle management, and role-based access control (RBAC).🚀 Live API EndpointURL: https://issue-tracker-backend-1-86vi.onrender.com🔐 Security ArchitectureThe API uses Spring Security with JWT (JSON Web Tokens) for stateless authentication.Authentication: Users log in via /api/auth/login to receive a JWT.Authorization: The JWT must be included in the Authorization: Bearer <token> header for all protected routes.RBAC: Endpoints are restricted based on roles (ROLE_STUDENT, ROLE_ADMIN).🏗️ Technical HighlightsCustom CORS Configuration: Seamlessly integrated with the Vercel-hosted frontend to allow secure cross-origin requests.DTO Pattern: Implemented Data Transfer Objects to decouple the database layer from the REST layer.Global Exception Handling: Centralized error management to provide consistent API responses.JPA/Hibernate: Advanced mapping for complex relationships between Users and Tickets.📊 Data ModelCode snippeterDiagram
    USER ||--o{ TICKET : creates
    USER {
        long id PK
        string email
        string password
        string role "STUDENT / ADMIN"
    }
    TICKET {
        long id PK
        string title
        string description
        string status "OPEN / RESOLVED"
        string priority "LOW / MEDIUM / HIGH"
        long created_by_id FK
    }
🚦 API Endpoints (Quick Reference)🔐 AuthenticationMethodEndpointDescriptionPOST/api/auth/registerRegister a new user (Student/Admin)POST/api/auth/loginAuthenticate and receive a JWT token🎫 Ticket ManagementMethodEndpointAccessDescriptionGET/api/ticketsStudent/AdminFetch tickets related to the userPOST/api/ticketsStudentCreate a new maintenance requestPUT/api/tickets/{id}AdminUpdate status or assign staff📂 Project StructurePlaintextsrc/main/java/com/sintu/issue_tracker
├── config/      # Security, CORS, and App configurations
├── controller/  # REST Endpoints (Auth, Tickets, Admin)
├── dto/         # Request and Response payloads
├── model/       # JPA Entities (User, Ticket, Enums)
├── repository/  # Spring Data JPA Interfaces
├── security/    # JWT Filter, Token Provider
└── service/     # Core Business Logic
🛠️ Local Development1. Clone the repositoryBashgit clone https://github.com/yourusername/issue-tracker-backend.git
cd issue-tracker-backend
2. Database ConfigurationUpdate src/main/resources/application.properties with your credentials:Propertiesspring.datasource.url=jdbc:postgresql://localhost:5432/issuetracker
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
app.jwt.secret=YOUR_64_CHARACTER_RANDOM_SECRET
app.admin.secret=MISHRA_BOSS
