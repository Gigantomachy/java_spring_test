package com.exercises.hellospring.service;

import java.util.List;

import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
//import com.exercises.hellospring.dto.PagedResponse;
import com.exercises.hellospring.dto.PagedBookResponseDTO;

import org.springframework.data.domain.Pageable;

public interface BookService {
    
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    List<BookResponseDTO> getBookSearchAdvanced(String keyword);
    PagedBookResponseDTO getBookSearch(Pageable pageable);
    //PagedResponse<BookResponseDTO> getBookSearch(String author, String title, int page, int size);

    BookResponseDTO createBook(BookRequestDTO book);
    BookResponseDTO updateBook(Long id, BookRequestDTO book);
    void deleteBook(Long id);
    

    BookResponseDTO addCategoryToBook(Long bookId, Long categoryId);
    void removeCategoryFromBook(Long bookId, Long categoryId);
}
