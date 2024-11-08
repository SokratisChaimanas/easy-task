---

# Tasks App

This is a full-stack Tasks application built with Angular 18 for the frontend and Spring Boot for the backend. The application allows users to log in and manage tasks by performing standard CRUD (Create, Read, Update, Delete) operations.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Setup Instructions](#setup-instructions)
  - [Backend (Spring Boot)](#backend-spring-boot)
  - [Frontend (Angular 18)](#frontend-angular-18)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **User Authentication**: Secure login for users to access the app.
- **Task Management**: Users can add, view, update, and delete tasks.
- **RESTful API**: Backend API endpoints for CRUD operations.
- **Responsive UI**: User-friendly and responsive design with Angular.

---

## Technologies

### Frontend
- **Angular 18**: Framework for building the frontend SPA with modern capabilities.
- **TypeScript**: Primary language for Angular development.

### Backend
- **Spring Boot**: Framework for building the backend REST API.
- **Spring Security**: For managing user authentication.
- **JPA (Java Persistence API)**: For database interactions.
- **MySQL**: Database used for persisting user and task data.

---

## Architecture

This project follows a Service-Oriented Architecture (SOA), designed to separate different layers of logic, data handling, and presentation. Each layer serves a specific purpose, allowing for a modular and maintainable codebase.

---

## Setup Instructions

### Backend (Spring Boot)

1. **Prerequisites**:
   - [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
   - [Gradle](https://gradle.org/)
   - [MySQL](https://www.mysql.com/) (or any other SQL database)

2. **Configuration**:
   - Create a MySQL database (e.g., `tasks_db`).
   - Update `application.properties` (or `application.yml`) with your database configuration:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/tasks_db
     spring.datasource.username=your_db_username
     spring.datasource.password=your_db_password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Run the Backend**:
   - Navigate to the backend directory:
     ```bash
     cd backend
     ```
   - Build and run the Spring Boot application with Gradle:
     ```bash
     ./gradlew bootRun
     ```
   - The backend API should be running on `http://localhost:8080`.

### Frontend (Angular 18)

1. **Prerequisites**:
   - [Node.js and npm](https://nodejs.org/)
   - [Angular CLI](https://angular.io/cli)

2. **Install Dependencies**:
   - Navigate to the frontend directory:
     ```bash
     cd frontend
     ```
   - Install Angular dependencies:
     ```bash
     npm install
     ```

3. **Run the Frontend**:
   - Start the Angular development server:
     ```bash
     ng serve
     ```
   - The frontend app should now be running on `http://localhost:4200`.

---

## Usage

1. **Access the App**:
   - Open your browser and go to `http://localhost:4200`.
   
2. **Login**:
   - Log in with your credentials to access task management features.

3. **Task Management**:
   - After logging in, you can:
     - **Create** a new task.
     - **View** a list of all your tasks.
     - **Update** details of existing tasks.
     - **Delete** tasks you no longer need.

4. **API Endpoints**:
   - You can access the backend API endpoints directly by making HTTP requests to `http://localhost:8080/api/...`.

---

## Project Structure

### Backend (Spring Boot)

```
backend/
├── src/main/java/gr/myprojects/easytask
│   ├── core               # Global used enums and exceptions
│   ├── dto                # Data Transfer Objects
│   ├── model              # Entity classes representing database tables
│   ├── repository         # Interfaces for data access
│   ├── rest               # REST controllers for handling API requests
│   ├── security           # Authentication configurations
│   ├── service            # Business logic layer
│   ├── validation         # Custom validators for input data
└── src/main/resources
    └── application.properties  # Application configuration
```

### Frontend (Angular 18)

```
frontend/
├── src
│   ├── app
│   │   ├── components          # Angular components for UI elements
│   │   ├── shared             
│   │       ├── guards          # Route guards for authentication
│   │       ├── interfaces      # TypeScript interfaces and models
│   │       └── services        # Shared services for tasks, authentication, etc.
│   ├── assets                  # Static assets
└── angular.json                # Angular CLI configuration
```


---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
