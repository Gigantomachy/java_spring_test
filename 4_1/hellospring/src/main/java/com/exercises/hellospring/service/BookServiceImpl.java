package com.exercises.hellospring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.exercises.hellospring.dto.AuthorSummaryDTO;
import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.dto.CategoryListDTO;
import com.exercises.hellospring.dto.PagedResponse;
import com.exercises.hellospring.exception.DuplicateResourceException;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.model.Author;
import com.exercises.hellospring.model.Book;
import com.exercises.hellospring.model.Category;
import com.exercises.hellospring.repository.AuthorRepository;
import com.exercises.hellospring.repository.BookRepository;
import com.exercises.hellospring.repository.CategoryRepository;

@Service
public class BookServiceImpl implements BookService {

    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        Optional<Book> currBook = bookRepository.findByIsbn(dto.getIsbn());
        if (currBook.isPresent()) {
            throw new DuplicateResourceException("Book with ISBN " + dto.getIsbn() + " already exists");
        }

        Book newBook = new Book(null, dto.getTitle(), author, dto.getYearPublished(), dto.getIsbn());
        bookRepository.save(newBook); // JPA save() handles both UPDATE (if id is present) and INSERT (if no id present)
        return mapToResponseDTO(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        List<BookResponseDTO> list = bookRepository.findAll().stream().map(b -> {
            AuthorSummaryDTO author = new AuthorSummaryDTO(b.getAuthor().getId(), b.getAuthor().getFirstName(), b.getAuthor().getLastName());
            return new BookResponseDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getIsbn(), author);
        }).toList();
        return list;
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponseDTO(book);
    }

    @Override
    public List<BookResponseDTO> getBookSearchAdvanced(String keyword) {
        List<BookResponseDTO> books = bookRepository.searchBooks(keyword).stream().map(b -> {
            AuthorSummaryDTO author = new AuthorSummaryDTO(b.getAuthor().getId(), b.getAuthor().getFirstName(), b.getAuthor().getLastName());
            return new BookResponseDTO(b.getId(), b.getTitle(), b.getYearPublished(), b.getIsbn(), author);
        }).toList();
        return books;
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book existing = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Optional<Book> isbnConflict = bookRepository.findByIsbn(dto.getIsbn());
        if (isbnConflict.isPresent() && !isbnConflict.get().getId().equals(id)) {
            throw new DuplicateResourceException("Book with ISBN " + dto.getIsbn() + " already exists");
        }

        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        existing.setTitle(dto.getTitle());
        existing.setAuthor(author);
        existing.setYearPublished(dto.getYearPublished());
        existing.setIsbn(dto.getIsbn());

        Book saved = bookRepository.save(existing);

        return mapToResponseDTO(saved);
    }

    @Override
    public PagedResponse<BookResponseDTO> getBookSearch(String author, String title, int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        // inefficient - fine for now
        List<BookResponseDTO> filtered = bookRepository.findAll().stream()
            .filter(b -> author == null || author.isBlank() || 
                        b.getAuthor().getLastName().toLowerCase().contains(author.toLowerCase()) ||
                        b.getAuthor().getFirstName().toLowerCase().contains(author.toLowerCase()))
            .filter(b -> title == null || title.isBlank() || 
                        b.getTitle().toLowerCase().contains(title.toLowerCase()))
            .map(b -> mapToResponseDTO(b))
            .toList();

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filtered.size());
        int totalPages = (filtered.size() + size - 1) / size;
        List<BookResponseDTO> result = fromIndex >= filtered.size() ? Collections.emptyList() : filtered.subList(fromIndex, toIndex);

        return new PagedResponse<>(result, page, size, filtered.size(), totalPages);
    }

    public BookResponseDTO addCategoryToBook(Long bookId, Long categoryId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        book.addCategory(category);
        book = bookRepository.save(book);
        return mapToResponseDTO(book);
    }

    public void removeCategoryFromBook(Long bookId, Long categoryId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        book.removeCategory(category);
        book = bookRepository.save(book);
    }
    
    private BookResponseDTO mapToResponseDTO(Book book) {
        AuthorSummaryDTO authorSummary = new AuthorSummaryDTO(
            book.getAuthor().getId(),
            book.getAuthor().getFirstName(),
            book.getAuthor().getLastName()
        );
        BookResponseDTO res = new BookResponseDTO(book.getId(), book.getTitle(), book.getYearPublished(), book.getIsbn(), authorSummary );

        Set<Category> categories = book.getCategories();
        CategoryListDTO catDTO = new CategoryListDTO();
        for (Category cat: categories) {
            catDTO.addCategory(cat.getName());
        }
        res.setCategories(catDTO);

        return res;
    }

}
