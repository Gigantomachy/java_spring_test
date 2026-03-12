package com.exercises.hellospring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exercises.hellospring.repository.AuthorRepository;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.model.Author;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Author> getAllAuthors() { return authorRepository.findAll(); }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }
}