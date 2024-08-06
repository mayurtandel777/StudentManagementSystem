# StudentManagementSystem
Student management system with jwt authentication

Description
This project is a Student Management System built using Java, Spring Boot, Spring Security, and Hibernate for database connectivity. It uses an in-memory H2 database for development and testing purposes. The system supports user roles including students and admins, with different functionalities for each role.

System Requirements
Java: JDK 11 or higher
Maven or Gradle for building the project


To ensure users can run your project smoothly, you should include detailed instructions in the README file on how to set up and run the project. This should cover all necessary steps, including system requirements, installation, configuration, and execution. Below is an enhanced version of the README.md file with added instructions for running the project:

Student Management System
Description
This project is a Student Management System built using Java, Spring Boot, Spring Security, and Hibernate for database connectivity. It uses an in-memory H2 database for development and testing purposes. The system supports user roles including students and admins, with different functionalities for each role.

Technologies Used
Java: Programming language
Spring Boot: Framework for building the application
Spring Security: For authentication and authorization
Hibernate: ORM for database interaction
H2 Database: In-memory database for development
System Requirements
Java: JDK 11 or higher
Maven or Gradle for building the project
Database Configuration
The project uses an in-memory H2 database with the following configuration:

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
Username: root
Password: password
Project Flow
Controllers
API Controller
Auth Controller
Endpoints
1. Register API (For Students)
URL: http://localhost:8080/api/Register

Method: POST

Request:

json
Copy code
{
    "firstName": "ADMIN",
    "lastName": "ADMIN",
    "email": "admin@gmail.com",
    "password": "admin123",
    "address": "PEN",
    "subjectIds": [1, 2, 3, 5]
}
Response:

json
Copy code
{
    "id": 1,
    "firstName": "MAYUR",
    "lastName": "TANDEL",
    "email": "admin@gmail.com",
    "password": "$2a$10$yCAEtDg16jVcWA4acRvYDeMNyR5FzQlGJHYGT02Cb.tTiWEdW1dkC",
    "address": "PEN",
    "role": "STUDENT",
    "subjects": [
        {
            "id": 2,
            "name": "Hindi"
        },
        {
            "id": 3,
            "name": "English"
        },
        {
            "id": 5,
            "name": "Science"
        },
        {
            "id": 1,
            "name": "Marathi"
        }
    ],
    "enabled": true,
    "authorities": [
        {
            "authority": "ROLE_STUDENT"
        }
    ],
    "username": "admin@gmail.com",
    "accountNonLocked": true,
    "accountNonExpired": true,
    "credentialsNonExpired": true
}
2. Login API (For Students and Admins)
URL: http://localhost:8080/api/login

Method: POST

Request:

For Students:

json
Copy code
{
    "email": "mayurtandel@gmail.com",
    "password": "mayurtandel"
}
For Admins:

json
Copy code
{
    "email": "admin@example.com",
    "password": "password123"
}
Response:

json
Copy code
{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcyMjkzNjAxMiwiZXhwIjoxNzIyOTM5NjEyfQ.H-9p9pQC_RiFv-S5Ftdn6gpEmh8aKfpCODGjzYpoIvE"
}
Note: The JWT token is valid for 1 hour.

Role-Based Access
Admin: Can manage students (create, delete by ID, get all students list).
Student: Can only get the list of subjects.


How to Run the Project
Clone the Repository:
git clone https://github.com/your-username/your-repo-name.git
Navigate to the Project Directory:
cd your-repo-name
Build and Run the Project:

If using Maven:
mvn clean install
mvn spring-boot:run

Access the Application:

The application runs on port 8080 by default. Access it via http://localhost:8080.

Technologies Used
Java: Programming language
Spring Boot: Framework for building the application
Spring Security: For authentication and authorization
Hibernate: ORM for database interaction
H2 Database: In-memory database for development


Database Configuration
The project uses an in-memory H2 database with the following configuration:

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
Username: root
Password: password

Admin username and password for login:-

{
    "email": "admin@example.com",
    "password": "password123"
}


Project Flow
Controllers
1]API Controller
2]Auth Controller

Endpoints
1. Register API (For Students)
URL: http://192.168.0.108:8080/api/Register

Method: POST

Request:

{
    "firstName": "ADMIN",
    "lastName": "ADMIN",
    "email": "admin@gmail.com",
    "password": "admin123",
    "address": "PEN",
    "subjectIds": [1, 2, 3, 5]
}


Response:-

{
    "id": 1,
    "firstName": "MAYUR",
    "lastName": "TANDEL",
    "email": "admin@gmail.com",
    "password": "$2a$10$yCAEtDg16jVcWA4acRvYDeMNyR5FzQlGJHYGT02Cb.tTiWEdW1dkC",
    "address": "PEN",
    "role": "STUDENT",
    "subjects": [
         {
         "id": 2,
            "name": "Hindi"
        },
        {
            "id": 3,
            "name": "English"
        },
        {
            "id": 5,
            "name": "Science"
        },
        {
            "id": 1,
            "name": "Marathi"
        }
    ],
    "enabled": true,
    "authorities": [
        {
            "authority": "ROLE_STUDENT"
        }
    ],
    "username": "admin@gmail.com",
    "accountNonLocked": true,
    "accountNonExpired": true,
    "credentialsNonExpired": true
}

Login API (For Students and Admins)
URL: http://192.168.0.108:8080/api/login

Method: POST

Request:
{
    "email": "mayurtandel@gmail.com",
    "password": "mayurtandel"
}

Response:-

{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcyMjkzNjAxMiwiZXhwIjoxNzIyOTM5NjEyfQ.H-9p9pQC_RiFv-S5Ftdn6gpEmh8aKfpCODGjzYpoIvE"
}

Note: The JWT token is valid for 1 hour.

Role-Based Access
Admin: Can manage students (create, delete by ID, get all students list).
Student: Can only get the list of subjects.

Contact
For any questions or suggestions, please contact mayurtandel777@gmail.com.

