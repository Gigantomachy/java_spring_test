package com.exercises.hellospring.dto;

public class BookResponseDTO {
    private String title;
    private String isbn;
    private int yearPublished;

    // we want the object version of long because it supports null - and id isn't always present (in POST for example)
    private Long id;
    private AuthorSummaryDTO authorSummary;
    private CategoryListDTO categories;

    public BookResponseDTO() {}

    public BookResponseDTO(Long id, String title, int yearPublished, String isbn, AuthorSummaryDTO author) {
        this.title = title;
        this.authorSummary = author;
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

    public AuthorSummaryDTO getAuthor() {
        return authorSummary;
    }

    public void setAuthor(AuthorSummaryDTO author) {
        this.authorSummary = author;
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

    public void setCategories(CategoryListDTO categories) {
        this.categories = categories;
    }

}
