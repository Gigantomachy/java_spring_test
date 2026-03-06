package com.exercises.hellospring.dto;

public class BookResponseDTO {
    private String title;
    private String author;
    private String isbn;
    private int yearPublished;

    // we want the object version of long because it supports null - and id isn't always present (in POST for example)
    private Long id;

    public BookResponseDTO() {}

    public BookResponseDTO(Long id, String title, String author, int yearPublished, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

}
