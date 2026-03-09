package com.exercises.hellospring.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.exception.DuplicateResourceException;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.model.Author;
import com.exercises.hellospring.model.Book;
import com.exercises.hellospring.repository.AuthorRepository;
import com.exercises.hellospring.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    
    @Mock BookRepository bookRepository; // fake BookRepository
    @Mock AuthorRepository authorRepository;
    @InjectMocks BookServiceImpl bookService; // real instance of BookServiceImpl, with the above 2 repositories injected

    // shared test data
    Author orwell;
    Book book1;
    Book book2;

    @BeforeEach
    void setUp() {
        orwell = new Author();
        orwell.setId(1L);
        orwell.setFirstName("George");
        orwell.setLastName("Orwell");

        book1 = new Book(1L, "1984", orwell, 1949, "978-0451524935");
        book2 = new Book(2L, "Animal Farm", orwell, 1945, "978-0452284241");
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));
        List<BookResponseDTO> result = bookService.getAllBooks();

        assertEquals(2, result.size());
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        BookResponseDTO result = bookService.getBookById(1L);

        assertEquals(book1.getTitle(), result.getTitle());
        assertEquals(book1.getIsbn(), result.getIsbn());
        assertEquals(book1.getId(), result.getId());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(99L));
    }

    @Test
    void testCreateBook() {
        BookRequestDTO request = new BookRequestDTO("A New Book", 1L, 2020, "978-1234567890");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(orwell));
        when(bookRepository.findByIsbn("978-1234567890")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(3L, "A New Book", orwell, 2020, "978-1234567890"));

        BookResponseDTO result = bookService.createBook(request);

        assertEquals("A New Book", result.getTitle());
        assertEquals(3L, result.getId());

    }

    @Test
    void testCreateBook_DuplicateIsbn() {
        BookRequestDTO request = new BookRequestDTO("A New Book", 1L, 2020, "978-0451524935");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(orwell));
        when(bookRepository.findByIsbn("978-0451524935")).thenReturn(Optional.of(book1)); // already exists

        assertThrows(DuplicateResourceException.class, () -> bookService.createBook(request));
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(99L));
    }

}
