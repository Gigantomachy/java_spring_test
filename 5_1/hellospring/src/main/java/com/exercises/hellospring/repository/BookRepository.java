package com.exercises.hellospring.repository;

import com.exercises.hellospring.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findByAuthor_LastNameContainingIgnoreCase(String lastName);
    List<Book> findByYearPublishedBetween(int startYear, int endYear);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCaseAndAuthor_LastNameContainingIgnoreCase(String title, String lastName);

    // JPQL (Java Persistence Query Language) - works with Java entity classes, not DB tables
    // here Book is the Java class and title is the Java field name
    @Query("SELECT b FROM Book b WHERE b.yearPublished >= :year ORDER BY b.title")
    List<Book> findBooksPublishedAfter(@Param("year") int year);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.name = :categoryName")
    List<Book> findBooksByCategory(@Param("categoryName") String categoryName);

    // native SQL query - bypasses the JPQL and queries directly against the DB
    // this is because HAVING and grouping with JOIN tables is cleaner and more reliable with raw SQL
    // downside is we need to change this if the DB changes
    @Query(value = "SELECT b.* FROM book b JOIN book_category bc ON b.id = bc.book_id GROUP BY b.id HAVING COUNT(bc.category_id) >= :minCategories", nativeQuery = true)
    List<Book> findBooksWithMinCategories(@Param("minCategories") int minCategories);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);
}
