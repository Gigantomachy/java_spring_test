package com.exercises.hellospring.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.exercises.hellospring.model.Book;

@Repository
public class InMemoryBookRepository implements BookRepository {

    List<Book> books;
    private AtomicLong counter;

    public InMemoryBookRepository() {
        this.books = new ArrayList<Book>();
        this.books.add(new Book(0L, "Clean Code", "Robert Martin", 2008,    "978-0132350884"));
        this.books.add(new Book(1L, "Something Funny", "Michael Man", 2020, "978-0044350639"));
        this.books.add(new Book(2L, "Random Stuff", "Derek Dude", 1990,    "979 -1326742384"));

        this.counter = new AtomicLong(3L);
    }

    @Override
    public boolean delete(Long id) {
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book nxt = it.next();
            if (nxt.getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public Optional<Book> findById(Long id) {
        //return books.stream().filter(b -> b.getId().equals(id)).findFirst();
        for (Book book: books) {
            if (book.getId().equals(id)) {
                Optional<Book> opt = Optional.of(book); // ofNullable()
                return opt;
            }
        }
        return Optional.empty();
    }

    @Override
    public Book save(Book book) {
        book.setId(counter.getAndIncrement());
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        for (Book updated : books) {
            if (updated.getId().equals(id)) {
                updated.setAuthor(book.getAuthor());
                updated.setTitle(book.getTitle());
                updated.setYearPublished(book.getYearPublished());
                updated.setIsbn(book.getIsbn());
                return Optional.ofNullable(book);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        for (Book book: books) {
            if (book.getIsbn().equals(isbn)) {
                Optional<Book> opt = Optional.of(book); // ofNullable()
                return opt;
            }
        }
        return Optional.empty();
    }
    
}
