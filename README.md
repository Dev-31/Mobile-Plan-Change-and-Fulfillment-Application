# Mobile Plan Change and Fulfillment Application

A full-stack web application that enables users to register, authenticate using OTP, browse mobile plans, manage subscriptions, and complete plan change or fulfillment workflows. The system is designed with an API-first approach and follows real-world telecom application patterns.

---

## 📌 Features

### 🔐 Authentication & Security
- User registration with email & mobile number
- OTP-based account verification
- Secure login with password + OTP
- JWT-based authentication
- Password reset using OTP
- Role-based access control (USER)

### 📱 Mobile Plan Management
- View available mobile plans
- Change or upgrade subscriptions
- Manage active plans
- Secure checkout flow

### 🛒 Orders & Subscriptions
- Cart management
- Order placement
- Subscription lifecycle handling

### 🤖 AI Assistance
- AI-powered plan explanations and assistance (API-driven)

---

## 🧰 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication
- MySQL
- Hibernate
- Maven

### Frontend
- React (Vite)
- Axios
- React Router
- Tailwind CSS

### Other Tools
- Swagger / OpenAPI
- Git & GitHub
- IntelliJ IDEA / VS Code

---

## 🔐 Authentication Flow

1. **Register**
   - User registers using mobile, email, and password
   - OTP sent to email
2. **Verify Registration**
   - OTP verification activates account
3. **Login**
   - Password verification
   - OTP sent to email
4. **Verify Login**
   - JWT token generated on successful OTP validation

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Node.js 18+
- MySQL
- Maven

---

### Backend Setup

```bash
cd mobileplan
mvn clean install
mvn spring-boot:run

