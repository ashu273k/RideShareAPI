# 🚗 RideShare Backend (Spring Boot + MongoDB + JWT)

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-green)
![Security](https://img.shields.io/badge/Security-JWT%20Auth-orange)
![Build](https://img.shields.io/badge/Build-Maven-blueviolet)

A minimal **RideShare backend API** built with **Spring Boot**, **MongoDB**, and **JWT authentication**.  
Users can register/login, create ride requests, and drivers can accept and complete rides.

---

## ✨ Features

- User registration & login with **BCrypt** password hashing
- **JWT-based** stateless authentication
- Role-based access: `ROLE_USER` and `ROLE_DRIVER`
- Ride lifecycle:
    - Request ride
    - Driver sees open requests
    - Driver accepts ride
    - User or driver completes ride
- Clean architecture:
    - Controller → Service → Repository
    - DTOs + validation
- MongoDB for persistence

---

## 🧱 Tech Stack

- **Java** 17+
- **Spring Boot** 3.x
    - Spring Web
    - Spring Data MongoDB
    - Spring Security
    - Jakarta Validation
- **JWT** (jjwt)
- **MongoDB**

---

## 📁 Project Structure

```text
src/main/java/org/example/rideshare
├── config
│ ├── JwtAuthenticationFilter.java
│ └── SecurityConfig.java
├── controller
│ └── AuthController.java
│ └── RideController.java
├── dto
│ ├── RegisterRequest.java
│ ├── LoginRequest.java
│ ├── LoginResponse.java
│ ├── CreateRideRequest.java
│ └── RideResponse.java
├── model
│ ├── User.java
│ └── Ride.java
├── repository
│ ├── UserRepository.java
│ └── RideRepository.java
├── service
│ ├── AuthService.java
│ └── RideService.java
└── util
└── JwtUtil.java
```

---

## ⚙️ Configuration

`src/main/resources/application.properties`:

```text
server.port=8080

spring.data.mongodb.uri=mongodb://localhost:27017/rideshare_db

app.jwt.secret=ThisIsASuperLongJwtSecretKey_ForHS512_MustBeAtLeastSixtyFourCharactersLong_1234567890abcdef
app.jwt.expiration-ms=360000
```

> In production, move `app.jwt.secret` and DB credentials to environment variables.

---

## ▶️ Running the App

1. Start MongoDB locally (default port `27017`).
2. Build & run:

mvn spring-boot:run


Server runs at: `http://localhost:8080`

---

## 🔐 Authentication Flow

### 1. Register

**POST** `/api/auth/register`

**Content-Type:** application/json

**Body:**

```text
{
"username": "john_user",
"password": "SecurePass123",
"role": "ROLE_USER"
}
```


### 2. Login

**POST** `/api/auth/login`

**Content-Type:** application/json

**Body:**
```text
{
"username": "john_user",
"password": "SecurePass123"
}
```
**Response:**
```text
{
"token": "<JWT_TOKEN>",
"username": "john_user",
"role": "ROLE_USER"
}
```

Use this token in all protected requests:

**Authorization:** Bearer <JWT_TOKEN>

---

## 🚘 Ride Endpoints (User)

### Create Ride

**POST** `/api/v1/rides`

**Headers:**
* **Authorization:** Bearer <JWT_TOKEN>
* **Content-Type:** application/json

**Body:**
```text
{
"pickupLocation": "Electronic City",
"dropLocation": "Whitefield"
}
```


### Get User Rides

**GET** `/api/v1/user/rides`

**Headers:**
* **Authorization:** Bearer <JWT_TOKEN>

---

## 🔧 TODO / Next Steps

- Add driver endpoints:
    - `GET /api/v1/driver/rides/requests`
    - `POST /api/v1/driver/rides/{rideId}/accept`
- Add complete ride endpoint:
    - `POST /api/v1/rides/{rideId}/complete`
- Global exception handling with custom error format
- Better error messages and logging
- Swagger/OpenAPI documentation

---


