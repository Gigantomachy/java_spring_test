package com.exercises.hellospring.model;

public class Book {

    // we want the object version of long because it supports null - and id isn't always present (in POST for example)
    private Long id;

    private String title;
    private String author;
    private int yearPublished;
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
