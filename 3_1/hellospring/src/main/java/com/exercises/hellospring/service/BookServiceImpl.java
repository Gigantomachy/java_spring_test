package com.exercises.hellospring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.dto.PagedResponse;
import com.exercises.hellospring.exception.DuplicateResourceException;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.model.Book;
import com.exercises.hellospring.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    BookServiceImpl(BookRepository repo) {
        this.bookRepository = repo;
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO book) {
        Optional<Book> currBook = bookRepository.findByIsbn(book.getIsbn());
        if (currBook.isPresent()) {
            throw new DuplicateResourceException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        Book newBook = new Book(null, book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getIsbn());
        bookRepository.save(newBook);
        return mapToResponseDTO(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        boolean res = bookRepository.delete(id);
        if (!res) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        List<BookResponseDTO> list = bookRepository.findAll().stream().map(b -> {
            return new BookResponseDTO(b.getId(), b.getTitle(), b.getAuthor(), b.getYearPublished(), b.getIsbn());
        }).toList();
        return list;
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponseDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO book) {
        // bad design - books really should be identified by isbn and not id, but that's beyond this scope

        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Optional<Book> currBook = bookRepository.findByIsbn(book.getIsbn());
        if (currBook.isPresent() && !currBook.get().getId().equals(id)) {
            throw new DuplicateResourceException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        Book newbook = bookRepository.update(id, new Book(null, book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getIsbn()))
                                     .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponseDTO(newbook);
    }

    @Override
    public PagedResponse<BookResponseDTO> getBookSearch(String author, String title, int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        List<BookResponseDTO> filtered = bookRepository.findAll().stream()
            .filter(b -> author == null || author.isBlank() || b.getAuthor().toLowerCase().contains(author.toLowerCase()))
            .filter(b -> title == null || title.isBlank() || b.getTitle().toLowerCase().contains(title.toLowerCase()))
            .map(b -> mapToResponseDTO(b))
            .toList();

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filtered.size());
        int totalPages = (filtered.size() + size - 1) / size;
        List<BookResponseDTO> result = fromIndex >= filtered.size() ? Collections.emptyList() : filtered.subList(fromIndex, toIndex);

        return new PagedResponse<>(result, page, size, filtered.size(), totalPages);
    }
    
    private BookResponseDTO mapToResponseDTO(Book book) {
        BookResponseDTO res = new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getIsbn() );
        return res;
    }

}
