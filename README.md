# Rest API - SPRING BOOT

## 🧱 Architecture and Technologies

This REST API is built with **Java Spring Boot**, following the principles of **Hexagonal Architecture** and the **Vertical Slice Architecture** pattern, allowing for better separation of responsibilities, scalability, and ease of maintenance.

### 🛠️ Main technologies

- **Spring Boot** – Core framework for building the API.
- **Spring Data JPA** – Persistence management with a decoupled data access layer.
- **Spring Security** – Robust authentication and authorization implementation.
- 
### Roles
- **CUSTOMER**
- **PROFESSIONAL**
- **ADMIN**
- **DEVOLEPER**

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

**2.a Create stored procedure**

````
DELIMITER //

CREATE PROCEDURE getUserPagination(
    IN PageIndex INT,
    IN PageSize INT
)
BEGIN
    DECLARE Offset INT;
    SET Offset = (PageSize * (PageIndex - 1));

    SELECT
        u.id,
        u.dni,
        u.email,
        u.first_name,
        u.last_name,
        u.role_name,
        @row_number:=@row_number + 1 AS Fila,
        (SELECT COUNT(*) FROM tbl_users) AS TotalFilas
    FROM 
        tbl_users u,
        (SELECT @row_number:=0) AS r
    ORDER BY u.id ASC
    LIMIT Offset, PageSize;
END //

DELIMITER ;
````

**2.b Test the created stored procedure**
````
-- Procedure call (page 1, 10 records per page)
CALL getUserPagination(1, 10);

-- Procedure call (page 2, 5 records per page)
CALL getUserPagination(2, 5);
````

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

**API Response Standard**
````
{
  "success": boolean,
  "message": string,
  "data": object | array | null,
  "error": object | null,
  "status": number
}
````

**Examples with users**
````
{
  "success": true,
  "message": "Users successfully obtained",
  "data": {
    "items": [
      {
        "id": "usr_20250717194721_fb87b56d",
        "dni": 12345678,
        "firstname": "Diego",
        "lastname": "Gomez",
        "email": "example1234@gmail.com",
        "role": "ADMIN"
      },
      {
        "id": "usr_20250717195912_5185abd1",
        "dni": 96325874,
        "firstname": "Valentina",
        "lastname": "Gomez",
        "email": "example3624@gmail.com",
        "role": "USER"
      }
    ],
    "pagination": {
      "totalItems": 8,
      "currentPage": 1,
      "perPage": 10,
      "totalPages": 1
    }
  },
  "error": null,
  "status": 200
}
````

**Error Response**
````
{
  "success": false,
  "message": "Something went wrong",
  "data": null,
  "error": {
    "message": "NotFoundException",
    "details": "Not found Exception . User does not exist with DNI: 469242361",
    "path": "/api/users/99998888"
  },
  "status": 400
}
````

The app defines following CRUD APIs.

### Auth

| Method | Url                | Decription | Sample Valid Request Body | 
| ------ |--------------------| ---------- | --------------------------- |
| POST   | /api/auth/register | Sign up | [JSON](#signup) |
| POST   | /api/auth/login    | Log in | [JSON](#signin) |

### Users

| Method | Url                               | Description    | Sample Valid Request Body |
|--------|-----------------------------------|----------------|---------------------------|
| GET    | /api/users                        | Returns all users. Default pagination values: pageIndex=1 and pageSize=5               |                           |
| GET    | /api/users?pageIndex=X&pageSize=X | Returns all users indicated by pagination           |
| GET    | /api/users/{dni}                  | Returns the user found by their ID. |                           |
| POST   | /api/users                        | Create a new user | [JSON](#usercreate)       |
| DELETE | /api/users/{dni}                  | Delete an existing user |                           |

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
