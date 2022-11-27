# Reactive Book store

### This is a practical assignment for the Senior Java Engineer position
### Features
* Uses reactive stack with R2DBC
* GET books or authors from DB (no authorization needed)
* Log in as ADMIN or AUTHOR user (see [schema.sql](src/main/resources/schema.sql) for sample users' data)
* Admin could modify authors list, authors could modify book list

### Configuration
To test the app you should add .env file with the following parameters:
* DB_NAME=_DATABASE_NAME_
* DB_USER=_DATABASE_USER_
* DB_PASS=_DATABASE_PASSWORD_
* DB_URL=jdbc:postgresql://database:5432/_DATABASE_NAME_
* SERVER_PORT=_PORT_FOR_API_

### Usage
* Get all books: GET domain/book
* Get all authors: GET domain/author
* Add new book: POST domain/book
* Add new author: POST domain/author
* Remove book: DELETE domain/book
* Remove author: DELETE domain/author

#### Example body for book adding:
```
{
    "title": "Book title",
    "description": "Book description",
    "publishingDate": "2022-11-01",
    "price": 20.0,
    "authorEmail": "author@test.com"
}
```

#### Example body to remove book
```
{
    "title": "Title of book to remove"
}
```

#### Example body to add author
```
{
    "email": "author@email.com",
    "name": "Author Name",
    "birthDate": "2022-11-01"
}
```

#### Example body to remove author
```
{
    "email": "author@toremove.com"
}
```