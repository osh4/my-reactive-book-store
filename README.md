# Book store demo

### This is a practical assignment for the Senior Java Engineer position
### Features
* GET books or authors from DB (no authorization needed)
* Log in as ADMIN or AUTHOR user (see [data.sql](src/main/resources/data.sql) for sample users' data)
* Admin could modify authors list, authors could modify book list

### Configuration
To test the app you should add .env file with the following parameters:
* DB_NAME=_DATABASE_NAME_
* DB_USER=_DATABASE_USER_
* DB_PASS=_DATABASE_PASSWORD_
* DB_URL=jdbc:postgresql://database:5432/_DATABASE_NAME_
* SERVER_PORT=_PORT_FOR_API_