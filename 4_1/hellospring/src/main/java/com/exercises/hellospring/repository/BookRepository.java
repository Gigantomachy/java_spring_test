package com.exercises.hellospring.repository;

import com.exercises.hellospring.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{

    // from JpaRepository<Book, Long> we get findAll, findById, save, deleteById, existsById, count etc...

    // derived query methods: findBy + field name + optional keyword
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByYearPublishedBetween(int startYear, int endYear);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

}
