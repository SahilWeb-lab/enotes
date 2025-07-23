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
│ │ ├── config/
│ │ ├── controller/
│ │ ├── dto/
│ │ ├── endpoint/
│ │ ├── enums/
│ │ ├── exception/
│ │ ├── handle/
│ │ ├── model/
│ │ ├── repository/
│ │ ├── schedular/
│ │ ├── service/
│ │ ├── service/impl/
│ │ ├── util/
│ │ └── EnotesApiServiceApplication.java
│ └── resources/
│ ├── application.properties
│ └── static
└── test/
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
## 👤 Author

**Sahil Kumar Mandal**

- 💼 [LinkedIn](https://www.linkedin.com/in/sahil-mandal-588380245/)
- 🐙 [GitHub](https://github.com/SahilWeb-lab)
- 📧 Email: [mandalsahil253@gmail.com](mailto:mandalsahil253@gmail.com)

---

## 🛠️ Installation & Run

```bash
# Clone the repository
git clone https://github.com/SahilWeb-lab/enotes.git
cd enotes

# Build and run
./mvnw spring-boot:run

