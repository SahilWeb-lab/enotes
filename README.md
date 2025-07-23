# 📝 eNotes - Notes Management API

**eNotes** is a lightweight and intuitive notes management RESTful API built using **Spring Boot**. It allows users to create, read, update, and delete personal notes. Ideal for learning purposes or as a foundation for more advanced personal productivity apps.

---

## 🚀 Features

- ✅ Create a new note  
- 📋 View all notes or a specific note by ID  
- ✏️ Update existing notes  
- ❌ Delete notes permanently  
- 🕓 Timestamps for created/updated notes  
- 🔐 Secure API with Spring Security (optional if implemented)  
---

## 📂 Project Structure
<pre><code>
src/
├── main/
│ ├── java/com/test/
│ │ ├── config/ # Configurations
│ │ ├── controller/ # REST API endpoints implementation
│ │ ├── dto/ # Data transfer objects
│ │ ├── endpoint/ # REST API endpoints declaration
│ │ ├── enums/ # Enum
│ │ ├── exception/ # Custom exception handling
│ │ ├── handle/ # Generic Response Handler
│ │ ├── model/ # Entity classes
│ │ ├── repository/ # Spring Data JPA repositories
│ │ ├── schedular/ # Schedular
│ │ ├── service/ # Business logic interface
│ │ ├── service/impl # Business logic implementation
│ │ ├── util # Utility Classes
│ │ └── EnotesApiServiceApplication.java # Main Spring Boot application
│ └── resources/
│ ├── application.properties # App configuration
│ └── static/ # Static resources (if any)
└── test/ # Unit and integration tests
</code></pre>
---

---

## 📦 Technologies Used

- Java 17+
- Spring Boot 3+
- Spring Web
- Spring Data JPA
- MySQL / H2
- Lombok
- Swagger (SpringDoc OpenAPI)
- Maven

---

