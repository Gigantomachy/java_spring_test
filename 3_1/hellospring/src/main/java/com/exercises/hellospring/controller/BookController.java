package com.exercises.hellospring.controller;

import com.exercises.hellospring.model.Book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private List<Book> books;

    public BookController() {
        this.books = new ArrayList<Book>();
        this.books.add(new Book(0L, "Clean Code", "Robert Martin", 2008,    "978-0132350884"));
        this.books.add(new Book(1L, "Something Funny", "Michael Man", 2020, "976-0044350639"));
        this.books.add(new Book(2L, "Random Stuff", "Derek Dude", 1990,    "018-1326742384"));
    }

    @GetMapping("")
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
    

}
