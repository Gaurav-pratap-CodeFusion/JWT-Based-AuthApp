# 🔐 Auth Service – JWT & Role Based Authentication (Spring Boot)

A secure **Authentication & Authorization** application built using **Spring Boot**, implementing **JWT-based authentication** and **Role-Based Access Control (RBAC)**. This project is designed following **clean architecture** and **Spring Security best practices**.

---

## 🚀 Features

* ✅ User Registration & Login
* 🔐 JWT-based Authentication (Stateless)
* 🧑‍💼 Role-Based Authorization (ADMIN / USER)
* 🛡️ Spring Security Filter Chain
* 🔁 Custom JWT Filter (`OncePerRequestFilter`)
* 🔑 Password Encryption using BCrypt
* 📦 Clean Layered Architecture
* ❌ No Session / No Cookies (Fully Stateless)

---

## 🧠 Authentication Flow

### 🔹 Login Flow

1. User sends **username & password**
2. `AuthenticationManager` authenticates credentials
3. `DaoAuthenticationProvider` verifies user via DB
4. On success → **JWT token generated**

### 🔹 Secured API Flow (JWT)

1. Client sends request with `Authorization: Bearer <JWT>`
2. JWT Filter (`OncePerRequestFilter`) executes
3. Token is validated (signature + expiry)
4. UserDetails loaded from DB
5. Authentication object set in `SecurityContextHolder`
6. Request forwarded to controller

---

## 🏗️ Project Structure

```
src/main/java
└── com.example.auth
    ├── controller
    │   └── AuthController.java
    ├── service
    │   ├── AuthService.java
    │   └── JwtService.java
    ├── security
    │   ├── JwtAuthenticationFilter.java
    │   ├── SecurityConfig.java
    │   └── UserDetailsServiceImpl.java
    ├── model
    │   ├── User.java
    │   └── Role.java
    ├── repository
    │   └── UserRepository.java
    └── dto
        ├── LoginRequest.java
        └── AuthResponse.java
```

---

## 🔐 Roles & Authorization

| Role  | Access            |
| ----- | ----------------- |
| USER  | User-level APIs   |
| ADMIN | Admin + User APIs |

Example:

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin")
public String adminOnly() {
    return "Admin access";
}
```

---

## 🔧 Tech Stack

* **Java 17+**
* **Spring Boot**
* **Spring Security**
* **JWT (JSON Web Token)**
* **Spring Data JPA**
* **MySQL / PostgreSQL**
* **Maven**

---

## ⚙️ Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

---

## 📌 API Endpoints

### 🔑 Auth APIs

| Method | Endpoint             | Description     |
| ------ | -------------------- | --------------- |
| POST   | `/api/auth/register` | Register user   |
| POST   | `/api/auth/login`    | Login & get JWT |

### 🔒 Secured APIs

| Method | Endpoint               | Role  |
| ------ | ---------------------- | ----- |
| GET    | `/api/user/profile`    | USER  |
| GET    | `/api/admin/dashboard` | ADMIN |

---

## 🧪 Testing

* Use **Postman** or **Swagger**
* Add header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🧠 Key Concepts Used

* `AuthenticationManager`
* `DaoAuthenticationProvider`
* `UsernamePasswordAuthenticationToken`
* `SecurityContextHolder`
* `OncePerRequestFilter`
* Stateless Authentication

---

## 🎯 Learning Outcome

This project helps understand:

* How Spring Security works internally
* Difference between Login-time & JWT-time authentication
* Filter Chain execution
* Role-based authorization

---

## 👨‍💻 Author

**Gaurav Pratap**
BSc IT Graduate | Java & Spring Boot Developer

---

## ⭐ Future Enhancements

* Refresh Token support
* OAuth2 / Social Login
* Swagger OpenAPI integration
* Rate limiting & audit logs

---

> 💡 *This project follows industry-level Spring Security standards and is suitable for interviews & real-world applications.*
