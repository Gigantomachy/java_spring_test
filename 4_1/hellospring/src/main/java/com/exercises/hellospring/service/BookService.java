package com.exercises.hellospring.service;

import java.util.List;

import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.dto.PagedResponse;

public interface BookService {
    
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    List<BookResponseDTO> getBookSearchAdvanced(String keyword);

    BookResponseDTO createBook(BookRequestDTO book);
    BookResponseDTO updateBook(Long id, BookRequestDTO book);
    void deleteBook(Long id);
    PagedResponse<BookResponseDTO> getBookSearch(String author, String title, int page, int size);

    BookResponseDTO addCategoryToBook(Long bookId, Long categoryId);
    void removeCategoryFromBook(Long bookId, Long categoryId);
}
