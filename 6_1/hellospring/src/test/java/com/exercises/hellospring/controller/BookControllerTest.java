package com.exercises.hellospring.controller;

import static org.mockito.Mockito.when;

import com.exercises.hellospring.dto.AuthorSummaryDTO;
import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.exception.ResourceNotFoundException;
import com.exercises.hellospring.security.JwtService;
import com.exercises.hellospring.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; // Spring auto-configures this in WebMvcTest

    @MockitoBean
    BookService bookService;

    @MockitoBean
    JwtService jwtService;

    BookResponseDTO dto1;
    BookResponseDTO dto2;

    @BeforeEach
    void setUp() {
        AuthorSummaryDTO author = new AuthorSummaryDTO(1L, "George", "Orwell");
        dto1 = new BookResponseDTO(1L, "1984", 1949, "978-0451524935", author);
        dto2 = new BookResponseDTO(2L, "Animal Farm", 1945, "978-0452284241", author);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(dto1, dto2));

        // $ = json root
        // $[0].title = first element title field
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("1984"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(dto1);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(99L)).thenThrow(new ResourceNotFoundException("Book not found"));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBook_Valid() throws Exception {
        BookRequestDTO request = new BookRequestDTO("A New Book", 1L, 2020, "978-1234567890");
        when(bookService.createBook(request)).thenReturn(dto1);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBook_Invalid() throws Exception {
        BookRequestDTO invalid = new BookRequestDTO("", 1L, 2020, "978-1234567890"); // blank title

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }


}
