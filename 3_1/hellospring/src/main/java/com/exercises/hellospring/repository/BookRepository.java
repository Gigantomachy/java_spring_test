package com.exercises.hellospring.repository;

import com.exercises.hellospring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    Optional<Book> update(Long id, Book book);
    boolean delete(Long id);
    Optional<Book> findByIsbn(String isbn);
}
