package com.exercises.hellospring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.exercises.hellospring.model.Book;
import com.exercises.hellospring.repository.BookRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    BookRepository repository;

    public DataSeeder(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Book(null, "Brave New World", "Aldous Huxley", 1932, "978-0060850524"));
        repository.save(new Book(null, "Fahrenheit 451", "Ray Bradbury", 1953, "978-1451673319"));
        repository.save(new Book(null, "The Old Man and the Sea", "Ernest Hemingway", 1952, "978-0684801469"));
        repository.save(new Book(null, "Dune", "Frank Herbert", 1965, "978-0441013593"));
        repository.save(new Book(null, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 1979, "978-0345391803"));
    }
    
}
