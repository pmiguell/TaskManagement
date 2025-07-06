# 📌 Task Manager - REST API with Spring Boot  

This is a **Task Manager** backend developed with **Spring Boot**, **Spring Security (JWT)**, and **PostgreSQL**. It allows users to create accounts, log in, and manage their tasks securely.  

---

## 🚀 Technologies Used  
- **Java 17**  
- **Spring Boot 3**  
- **Spring Security & JWT**  
- **Spring Data JPA & Hibernate**  
- **PostgreSQL**  
- **Lombok**  
- **Swagger (OpenAPI)**  

---

## 📌 Features  
✅ User registration and authentication (JWT)  
✅ Task CRUD operations (Create, Read, Update, Delete)  
✅ Secure endpoints with authentication  
✅ Task filtering by status (pending/completed/in progress)  
✅ API documentation with Swagger  

---

## ⚙️ How to Run the Project Locally  

### 1️⃣ Prerequisites  
- **Java 17** installed  
- **PostgreSQL** installed and running  

### 2️⃣ Configure the Database  
Create a PostgreSQL database named `task_manager` and update the `application.properties` file:  

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run the Project
Clone the repository and execute the following commands: 

```bash
git clone https://github.com/pmiguell/TaskManagement.git
cd TaskManagement
mvn spring-boot:run
```

The API will be available at http://localhost:8080.

## 🛠 API Endpoints

### 📌 Authentication 

| Method | Endpoint        | Description              | Authentication |
|--------|----------------|------------------------|--------------|
| POST   | `/auth/register` | Register a new user | ❌ |
| POST   | `/auth/login`    | Login (generates JWT)        | ❌ |

### 📌 Tarefas  

| Method | Endpoint                 | Description                                    | Authentication |
|--------|---------------------------|----------------------------------------------|--------------|
| GET    | `/tasks`                  | Get all tasks of the authenticated user | ✅ |
| POST   | `/tasks`                  | Create a new task                         | ✅ |
| GET    | `/tasks/{id}`             | Get a specific task by ID           | ✅ |
| PUT    | `/tasks/{id}`             | 	Update an existing task                | ✅ |
| DELETE | `/tasks/{id}`             | 	Delete a task by ID                     | ✅ |
| DELETE | `/tasks`                  | Delete all tasks of the authenticated user | ✅ |
| PATCH  | `/tasks/conclude/{id}`    | Mark a task as **COMPLETED**          | ✅ |
| PATCH  | `/tasks/undo-conclude/{id}` | Undo task completion             | ✅ |
| GET    | `/tasks/filter`           | Filter tasks based on criteria         | ✅ |
| GET    | `/tasks/categories`       | 	Get all user task categories | ✅ |

✅ **Authentication:** All task endpoints require a JWT token in the request header:  
```http
Authorization: Bearer <your_token>
```

## 📖 API Documentation

Once the application is running locally, the full API documentation can be accessed through Swagger UI at:  
🔗 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)  
