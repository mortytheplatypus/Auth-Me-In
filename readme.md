# Auth Me In

## Overview
Auth Me In is a web application that provides user registration and authentication features. The application is built using React for the frontend and Spring Boot for the backend. It allows users to create accounts, log in, and manage their sessions securely.

## Table of Contents
- [Auth Me In](#auth-me-in)
  - [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Technologies](#technologies)
  - [Frontend Setup](#frontend-setup)
  - [Backend Setup](#backend-setup)
  - [API Endpoints](#api-endpoints)
  - [Usage](#usage)

## Features
- User registration with validation
- User login and session management
- Error handling and user feedback via toast notifications
- Responsive design for mobile and desktop

## Technologies
- **Frontend**: React, React Router, React Toastify
- **Backend**: Spring Boot, Java 17, Spring Security, JPA (or any other persistence framework)
- **Database**: MySQL, PostgreSQL, or any other database of your choice
- **Styling**: CSS

## Frontend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/mortytheplatypus/auth-me-in-frontend.git
   cd auth-me-in-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Open your browser and navigate to `http://localhost:3000`.

## Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/mortytheplatypus/auth-me-in-backend.git
   cd auth-me-in-backend
   ```

2. Ensure you have Java 17 and Maven installed.

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Set up your application properties (e.g., database connection string, JWT secret) in `src/main/resources/application.yml`.

5. Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

6. The backend will run on `http://localhost:8000` (or your specified port).

## API Endpoints
- **POST /auth/register**: Register a new user
- **POST /auth/login**: Log in an existing user
- **GET /auth/logout**: Log out the current user

## Usage
- Navigate to the registration page to create a new account.
- After registration, you can log in using your credentials.
- Upon successful login, you will be redirected to the home page.



