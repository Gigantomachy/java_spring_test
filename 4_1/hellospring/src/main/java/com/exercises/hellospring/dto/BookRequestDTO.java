package com.exercises.hellospring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookRequestDTO {

    // validation happens during data binding - Jackson deserializes the object and object is populated
    // then the validator runs - before the @PostMapping and @PutMapping etc... methods are executed

    @NotBlank(message = "title is required")
    @Size(max = 200)
    private String title;

    @NotNull(message = "Author is required")
    private Long authorId;

    @NotBlank
    @Pattern(regexp = "^(978|979)-\\d{10}$", message = "ISBN must be in format 978-XXXXXXXXXX or 979-XXXXXXXXXX")
    private String isbn;

    @Min(1450)
    private int yearPublished;

    // Jackson prefers default constructor + setters
    public BookRequestDTO() {}

    public BookRequestDTO(String title, Long authorId, int yearPublished, String isbn) {
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(Long author) {
        this.authorId = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYearPublished() {
        return yearPublished;
    }
    
}
