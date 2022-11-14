-- Sample data to check book store app functions
-- default pass is nimda321
INSERT INTO book_store_user(email, name, birth_date, password, dtype)
VALUES ('admin@test.com', 'Admin', null, '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        'BookStoreUser'),
       ('author1@test.com', 'Author1', '10-10-1990', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        'Author 1'),
       ('author2@test.com', 'Author2', '11-11-1981', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.7ePbCSemAZgwYRI2srGPAeE24kMEyqK',
        'Author 2');

INSERT INTO role(name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_AUTHOR');

INSERT INTO book_store_users_roles(book_store_user_email, role_name)
VALUES ('admin@test.com', 'ROLE_ADMIN'),
       ('author1@test.com', 'ROLE_AUTHOR'),
       ('author2@test.com', 'ROLE_AUTHOR');

INSERT INTO book(title, description, price, publishing_date, author_email)
VALUES ('Test book 1', 'Amazing 1st test book', 12.2, '2010-01-01', 'author1@test.com'),
       ('Test book 2', 'Boring 2nd test book', 34.4, '2000-01-01', 'author2@test.com')