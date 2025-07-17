# Rest API - SPRING BOOT

## 🧱 Arquitectura y Tecnologías

Esta API REST está construida con **Java Spring Boot**, siguiendo los principios de la **Arquitectura Hexagonal** y el patrón **Vertical Slice Architecture**, lo que permite una mejor separación de responsabilidades, escalabilidad y facilidad de mantenimiento.

### 🛠️ Tecnologías principales

- **Spring Boot** – Framework principal para la construcción de la API.
- **Spring Data JPA** – Manejo de persistencia con una capa de acceso a datos desacoplada.
- **Spring Security** – Implementación de autenticación y autorización robusta.

### Roles
- **USER**
- **ADMIN**

## Steps to Setup

## ⚙️ Configuración
**Requisitos**:
   - JDK 21
   - Maven

**1. Clone the Project**

```bash
$ git clone https://github.com/GomezDiegoElias/API-REST-MDS-TUP-2025.git
```

**2. Create Mysql database**
```bash
$ create database bd_tup_mds_api
```
- run `src/main/resources/blogapi.sql`

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application-dev.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using maven**

```bash
$ mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

Swagger Documentation <http://localhost:8080/swagger-ui/index.html>

## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url                | Decription | Sample Valid Request Body | 
| ------ |--------------------| ---------- | --------------------------- |
| POST   | /api/auth/register | Sign up | [JSON](#signup) |
| POST   | /api/auth/login    | Log in | [JSON](#signin) |

### Users

| Method | Url              | Description                       | Sample Valid Request Body |
|--------|------------------|-----------------------------------|---------------------------|
| GET    | /api/users       | Returns all users                 |                           |
| GET    | /api/users/{dni} | Returns the user found by their ID. |                           |
| POST   | /api/users       | Create a new user          | [JSON](#usercreate)       |
| DELETE | /api/users/{dni} | Delete an existing user        |                           |

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /api/auth/register</a>
```json
{
   "dni": 12345678, 
   "firstName": "Diego", 
   "lastName": "Gomez", 
   "email": "example123@gmail.com", 
   "password": "password123"
}
```

##### <a id="signin">Log In -> /api/auth/login</a>
```json
{
	"email": "example123@gmail.com",
	"password": "password123"
}
```

##### <a id="usercreate">Create User -> /api/users</a>
```json
{
   "dni": 12345678,
   "firstname": "Valentina",
   "lastname": "Gomez",
   "email": "example789@gmail.com",
   "password": "123456",
   "role": "USER"
}
```
