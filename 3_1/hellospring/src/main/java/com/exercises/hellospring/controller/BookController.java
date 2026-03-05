package com.exercises.hellospring.controller;

import com.exercises.hellospring.model.Book;

import jakarta.validation.Valid;

import com.exercises.hellospring.dto.PagedResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private List<Book> books;
    private AtomicLong counter;

    public BookController() {
        this.books = new ArrayList<Book>();
        this.books.add(new Book(0L, "Clean Code", "Robert Martin", 2008,    "978-0132350884"));
        this.books.add(new Book(1L, "Something Funny", "Michael Man", 2020, "976-0044350639"));
        this.books.add(new Book(2L, "Random Stuff", "Derek Dude", 1990,    "018-1326742384"));

        this.counter = new AtomicLong(3L);
    }

    @GetMapping
    public List<Book> getBooks() {
        return this.books;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        for ( Book bk: books ) {
            if (bk.getId() == id) {
                return bk;
            }
        }
        return null;
    }

    @GetMapping("/search")
    public PagedResponse<Book> getBookSearch(@RequestParam(required = false)     String author, 
                                @RequestParam(required = false)     String title, 
                                @RequestParam(defaultValue = "0")   int page, 
                                @RequestParam(defaultValue = "10")  int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 0);
        
        List<Book> filteredList = books.stream().filter(b -> author == null || author.isBlank() || b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                                                .filter(b -> title == null || title.isBlank() || b.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
        
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filteredList.size());
        int totalPages = (filteredList.size() + size - 1) / size;

        List<Book> result;

        if (fromIndex >= filteredList.size()) {
            result = Collections.emptyList();
        } else {
            result = filteredList.subList(fromIndex, toIndex);
        }

        PagedResponse<Book> response = new PagedResponse<>(result, page, size, filteredList.size(), totalPages);
        
        return response;
    }
    
    
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        book.setId(counter.getAndIncrement());
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> replaceBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        for (Book bk : books) {
            if (bk.getId().equals(id)) { 
                bk.setTitle(book.getTitle());
                bk.setAuthor(book.getAuthor());
                bk.setYearPublished(book.getYearPublished());
                bk.setIsbn(book.getIsbn());
                return ResponseEntity.ok(bk);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Iterator<Book> it = books.iterator();
        while(it.hasNext()) {
            Book bk = it.next();
            if (bk.getId() == id) {
                it.remove();
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
