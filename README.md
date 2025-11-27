# 🌍 Travelverse Recommendation System
## Smart Geospatial Recommendation Engine for Travel Experiences

Travelverse Recommendation System is a modular recommendation engine that processes travel entities (places, POIs, events) and returns a ranked list of recommendations based on geospatial data, user preferences, and dynamic load metrics.

This project is part of the Travelverse platform — an intelligent AI assistant for travelers.
## 🚀 Key Features

### 🔥 Recommendation Engine

- Multifactor scoring system:
    - Distance Score
    - Business Score
    - Hidden Gem Score
    - Budget Match Score
    - Popularity & Load Score
    - Category Match Score
- Automatic normalization of active weights
- Graceful deactivation of unavailable criteria

### 📊 Entity Intelligence

- Weekly load metric
- Traffic growth predictio
- Quiet/peak hours analysis

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.3.4
- **Language**: Java 21
- **Database**: MySQL with JPA/Hibernate
- **Build Tool**: Maven
- **Database Migration**: Liquibase
- **Object Mapping**: MapStruct
- **Testing**: JUnit Jupiter

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd web3-events-app
```

### 2. Configure Database
Create a MySQL database and update connection settings in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/web3_events
    username: your_username
    password: your_password
```

### 3. Build and Run
```bash
# Build the application
mvn clean package

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## 🏗️ Development Guidelines

### Code Style
- Follow Spring Boot best practices
- Use Lombok for reducing boilerplate code

### Architecture Patterns
The application follows several key architectural patterns:
- **Layered Architecture** - Clear separation of concerns
- **Repository Pattern** - Data access abstraction
- **Service Layer Pattern** - Business logic encapsulation
- **DTO Pattern** - Data transfer between layers

For detailed information about architectural patterns, see [**Architectural Patterns**](docs/architectural-patterns.md).

## 📄 License

This project is proprietary software owned by OneWorldOnline.

---
