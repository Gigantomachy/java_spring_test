-- V1__Create_tables.sql
-- Creates the initial schema matching JPA entities: Author, Book, Category, book_category join table

CREATE TABLE author (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    biography   TEXT,
    birth_date  DATE
);

CREATE TABLE category (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE book (
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    isbn           VARCHAR(255) NOT NULL UNIQUE,
    year_published INTEGER      NOT NULL,
    author_id      BIGINT       NOT NULL,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE book_category (
    book_id     BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    CONSTRAINT fk_bc_book     FOREIGN KEY (book_id)     REFERENCES book(id),
    CONSTRAINT fk_bc_category FOREIGN KEY (category_id) REFERENCES category(id)
);
