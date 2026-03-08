package com.exercises.hellospring.controller;

import com.exercises.hellospring.service.BookService;

import jakarta.validation.Valid;

import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.dto.PagedResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    BookService bookService;

    public BookController(BookService service) {
        this.bookService = service;
    }

    @GetMapping
    public List<BookResponseDTO> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search")
    public PagedResponse<BookResponseDTO> getBookSearch(
                                                        @RequestParam(required = false)     String author, 
                                                        @RequestParam(required = false)     String title, 
                                                        @RequestParam(defaultValue = "0")   int page, 
                                                        @RequestParam(defaultValue = "10")  int size) {
        return bookService.getBookSearch(author, title, page, size);
    }

    @GetMapping("/search/advanced")
    public List<BookResponseDTO> getBookSearchAdvanced(@RequestParam(required = true) String keyword) {
        return bookService.getBookSearchAdvanced(keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.status(200).body(bookService.getBookById(id));
    }
    
    // without @Valid, the validator won't run - even if fields are annotated in Book
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @PostMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<BookResponseDTO> addCategoryToBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.addCategoryToBook(bookId, categoryId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> replaceBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromBook(@PathVariable Long bookId, @PathVariable Long categoryId) {
        bookService.removeCategoryFromBook(bookId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
