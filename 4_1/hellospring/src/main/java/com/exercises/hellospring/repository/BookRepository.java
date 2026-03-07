package com.exercises.hellospring.repository;

import com.exercises.hellospring.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findByAuthor_LastNameContainingIgnoreCase(String lastName);
    List<Book> findByYearPublishedBetween(int startYear, int endYear);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String title, String lastName);

}
