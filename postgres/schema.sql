set schema 'public';

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    email      varchar(64) PRIMARY KEY,
    name       varchar(64),
    password   varchar(64),
    roles      varchar(64)[],
    birth_date date,
    type       varchar(64)
);
DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    title           varchar(200) PRIMARY KEY,
    description     varchar(255),
    publishing_date date,
    price           decimal(10, 2),
    author_email    varchar(64)
);
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    name        varchar(200) PRIMARY KEY,
    user_emails varchar(64)[]
);

INSERT INTO users(email, name, password, roles, birth_date, type)
VALUES ('admin@test.com', 'Admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        ARRAY ['ROLE_ADMIN'], null, 'ADMIN'),
       ('author1@test.com', 'Author1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        ARRAY ['ROLE_AUTHOR'], '2000-01-01', 'AUTHOR'),
       ('author2@test.com', 'Author2', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        ARRAY ['ROLE_AUTHOR'], '2010-01-01', 'AUTHOR');

INSERT INTO books(title, description, price, publishing_date, author_email)
VALUES ('Test book 1', 'Amazing 1st test book', 12.2, '2010-01-01', 'author1@test.com'),
       ('Test book 2', 'Boring 2nd test book', 34.4, '2000-01-01', 'author2@test.com')