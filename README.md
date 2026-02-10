#  VaultShare — Enterprise Secure File Sharing Platform

VaultShare is an enterprise-style secure file sharing platform designed to provide organizations with a safe, efficient, and auditable way to share files. With built-in encryption, role-based access control, and simulated file scanning, VaultShare ensures that sensitive data remains secure both at rest and in transit.

---

##  Features

* **AES/RSA File Encryption** – Files are encrypted before storage using AES symmetric encryption, with RSA for secure key exchange.
* **Secure Upload/Download** – All file transfers are encrypted over HTTPS.
* **Role-Based Access Control (RBAC)** – Admins, users, and guests have configurable access permissions.
* **File Scanning Simulation** – Optional automated file scanning before storage to simulate malware detection.
* **Audit Logs** – All file operations are logged for compliance and security auditing.

---

##  Tech Stack

* **Backend:** Spring Boot
* **Security:** Spring Security (Authentication & Authorization)
* **Database:** PostgreSQL or MySQL
* **Containerization:** Docker & Docker Compose
* **Optional:** Swagger/OpenAPI for API documentation

---

##  Getting Started

### Prerequisites

* Java 17+
* Maven 3+
* Docker & Docker Compose
* PostgreSQL/MySQL

---

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/vaultshare.git
cd vaultshare
```

---

### 2. Configure the Database

Update `application.properties` or `application.yml` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vaultshare
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

---

### 3. Build the Application

```bash
mvn clean install
```

---

### 4. Run with Docker (Optional)

Build and start the containers:

```bash
docker-compose up --build
```

This will spin up the backend and the database in isolated containers.

---

### 5. Running Locally

```bash
mvn spring-boot:run
```

The backend server will start at `http://localhost:8080`.

---

##  Security Details

* **AES Encryption**: Used for encrypting file contents.
* **RSA Encryption**: Used for securely exchanging encryption keys.
* **Spring Security**: Handles authentication and role-based access control.
* **HTTPS**: All file transfers are encrypted.

---

##  API Endpoints (Examples)

| Endpoint              | Method | Description                 |
| --------------------- | ------ | --------------------------- |
| `/api/files/upload`   | POST   | Upload a file (encrypted)   |
| `/api/files/download` | GET    | Download a file (decrypted) |
| `/api/files/list`     | GET    | List accessible files       |
| `/api/users`          | POST   | Create a new user           |
| `/api/audit/logs`     | GET    | Retrieve file activity logs |

> Full API documentation is available via Swagger at `/swagger-ui.html`.

---

##  Roles

* **Admin:** Full access to all files, users, and audit logs.
* **User:** Can upload, download, and list own files.
* **Guest:** Read-only access (if allowed by admin).

---

##  Project Structure

```
vaultshare/
├── src/main/java/com/vaultshare/
│   ├── controller/       # REST API controllers
│   ├── service/          # Business logic
│   ├── security/         # Spring Security configurations
│   ├── repository/       # JPA Repositories
│   └── model/            # Entities
├── src/main/resources/
│   └── application.properties
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

##  Audit & Logging

VaultShare logs every action performed on files, including:

* Uploads & downloads
* File deletions
* Access attempts
* User role changes

Logs are stored in the database for easy retrieval and auditing.

---

##  Contributing

Contributions are welcome!
Please open an issue or submit a pull request with improvements or bug fixes.

---

###  Optional Enhancements

* Integrate real malware scanning via ClamAV or VirusTotal API
* Implement multi-factor authentication (MFA)
* Add file versioning & soft-delete functionality
* Build a React/Angular frontend for a complete SaaS experience

---

