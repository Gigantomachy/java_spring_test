package com.exercises.hellospring.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

    // we want the object version of long because it supports null - and id isn't always present (in POST for example)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // @JsonIgnoreProperties important to prevent infinite recursion during serialization (jackson) 
    // - more of a safety net in case we return Book from a Controller
    // alternatively we can remove Jackson annotations and make this fail loudly (only DTOs should interact with Jackson)
    @ManyToOne(fetch = FetchType.LAZY) // books own the relationship
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Author author;
    private int yearPublished;

    @Column(unique = true)
    private String isbn;

    @ManyToMany 
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("books")
    private Set<Category> categories;

    // JPA requires a no arg constructor
    public Book() {
        this.categories = new HashSet<>();
    }

    public Book(Long id, String title, Author author, int yearPublished, String isbn) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.id = id;
        this.categories = new HashSet<>();
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
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

}
