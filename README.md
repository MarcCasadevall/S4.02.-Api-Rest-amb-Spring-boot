S4.02. REST API with Spring Boot
Description: REST API built with Spring Boot that manages a fruit inventory, suppliers and customer orders. The project evolves through three levels, each using a different database.

📌 Exercise Description
Development of three independent REST APIs with Spring Boot, each with a full CRUD over different entities and databases:

Level 1: Fruit CRUD with an in-memory H2 database.
Level 2: Fruit and supplier CRUD with MySQL, including a @ManyToOne relationship.
Level 3: Fruit order CRUD with MongoDB, using embedded documents.


✨ Features
Level 1 — Fruit API (H2)

Create, read, update and delete fruits
Input validation with Bean Validation
Global exception handling with GlobalExceptionHandler

Level 2 — Fruit API (MySQL)

Full CRUD for fruits and suppliers
@ManyToOne relationship between Fruit and Provider
Filter fruits by supplier using Query Params
Restriction on deleting suppliers with associated fruits

Level 3 — Order API (MongoDB)

Full CRUD for fruit orders
Embedded documents with a list of OrderItem
Delivery date validation (minimum tomorrow)
Non-empty product list validation


🛠 Technologies

Backend: Java 21, Spring Boot 3, Spring Data JPA, Spring Data MongoDB
Databases: H2 (level 1), MySQL 8.0 (level 2), MongoDB 7.0 (level 3)
Validation: Jakarta Bean Validation
Testing: JUnit 5, Mockito, MockMvc
Containers: Docker, Docker Compose
Tools: Maven, Lombok, IntelliJ IDEA, Postman


🚀 Installation and Setup
1. Clone the repository
   bashgit clone https://github.com/user/S4.02-Api-Rest-amb-Spring-boot.git
   cd S4.02-Api-Rest-amb-Spring-boot/fruit-api-h2
2. Environment variables
   Create a .env file at the root of the project with the following content:
   DB_URL=jdbc:mysql://mysql:3306/fruit_db
   DB_USERNAME=root
   DB_PASSWORD=root
3. Running the application
   Option A — Docker Compose (recommended):
   bashdocker compose up --build -d
   This automatically starts MySQL, MongoDB and the Spring Boot application.
   Option B — IntelliJ IDEA (development):
   First start the databases:
   bashdocker compose up mysql mongodb -d
   Then run FruitApiH2Application from IntelliJ.
   The application will be available at: http://localhost:8080
4. Tests
   Run all tests from the terminal:
   bash./mvnw test
   Or from IntelliJ by right-clicking the test folder → Run All Tests.

📸 Demo
Available endpoints
MethodEndpointDescriptionPOST/fruitsCreate a fruitGET/fruitsList all fruitsGET/fruits/{id}Get a fruit by IDPUT/fruits/{id}Update a fruitDELETE/fruits/{id}Delete a fruitPOST/providersCreate a supplierGET/providersList all suppliersPUT/providers/{id}Update a supplierDELETE/providers/{id}Delete a supplierGET/fruits?providerId={id}Get fruits by supplierPOST/ordersCreate an orderGET/ordersList all ordersGET/orders/{id}Get an order by IDPUT/orders/{id}Update an orderDELETE/orders/{id}Delete an order

🧩 Diagrams and technical decisions
Layered architecture (MVC)
Controller  →  handles HTTP requests
Service     →  business logic
Repository  →  database access
Model       →  entities / documents
DTO         →  data transfer
Exception   →  global error handling
Technical decisions

DTOs: used to avoid exposing JPA/MongoDB entities directly, controlling input and output data.
GlobalExceptionHandler: centralizes error handling, returning clean JSON responses with the correct HTTP status code.
Environment variables: database credentials are never hardcoded, they are injected via .env.
Multi-stage Dockerfile: separates the build phase from the production phase, generating a lightweight image with only the .jar.
TDD: tests were written before the code to ensure expected behaviour from the start.