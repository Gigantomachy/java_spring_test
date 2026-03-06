package com.exercises.hellospring.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Book {

    // we want the object version of long because it supports null - and id isn't always present (in POST for example)
    private Long id;

    // validation happens during data binding - Jackson deserializes the object and object is populated
    // then the validator runs - before the @PostMapping and @PutMapping etc... methods are executed

    @NotBlank(message = "title is required")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Min(1450)
    private int yearPublished;

    @NotBlank
    @Pattern(regexp = "^(978|979)-\\d{10}$", message = "ISBN must be in format 978-XXXXXXXXXX or 979-XXXXXXXXXX")
    private String isbn;

    // Jackson prefers default constructor + setters
    public Book() {}

    public Book(Long id, String title, String author, int yearPublished, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
