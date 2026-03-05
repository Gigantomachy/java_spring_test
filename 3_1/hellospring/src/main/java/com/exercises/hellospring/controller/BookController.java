package com.exercises.hellospring.controller;

import com.exercises.hellospring.model.Book;

import org.apache.catalina.connector.Response;
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
import java.util.Iterator;

// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




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
    
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book.setId(counter.getAndIncrement());
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> replaceBook(@PathVariable Long id, @RequestBody Book book) {
        for (Book bk: books) {
            if (bk.getId() == book.getId()) {
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
