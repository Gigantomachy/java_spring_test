package com.exercises.hellospring;

import com.exercises.hellospring.dto.AuthorSummaryDTO;
import com.exercises.hellospring.dto.BookRequestDTO;
import com.exercises.hellospring.dto.BookResponseDTO;
import com.exercises.hellospring.repository.AuthorRepository;
import com.exercises.hellospring.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class BookIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    Long orwellId;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        // DataSeeder already ran on startup, so authors exist — just grab Orwell's id
        orwellId = authorRepository.findAll().stream()
                .filter(a -> a.getLastName().equals("Orwell"))
                .findFirst()
                .orElseThrow()
                .getId();
    }

    @Test
    void testCreateAndGetBook() {
        BookRequestDTO request = new BookRequestDTO("A New Book", orwellId, 2020, "978-1234567890");

        // BookResponseDTO.class is a type token - we pass this so Java knows what class to deserialize the JSON it receives into
        ResponseEntity<BookResponseDTO> createResponse = restTemplate.postForEntity(
                "/api/books", request, BookResponseDTO.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());

        Long id = createResponse.getBody().getId();

        ResponseEntity<BookResponseDTO> getResponse = restTemplate.getForEntity(
                "/api/books/" + id, BookResponseDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("A New Book", getResponse.getBody().getTitle());
    }

    @Test
    void testGetAllBooks() {
        restTemplate.postForEntity("/api/books", new BookRequestDTO("Book One", orwellId, 2020, "978-1111111111"), BookResponseDTO.class);
        restTemplate.postForEntity("/api/books", new BookRequestDTO("Book Two", orwellId, 2021, "978-2222222222"), BookResponseDTO.class);
        restTemplate.postForEntity("/api/books", new BookRequestDTO("Book Three", orwellId, 2022, "978-3333333333"), BookResponseDTO.class);

        ResponseEntity<List<BookResponseDTO>> response = restTemplate.exchange(
                "/api/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookResponseDTO>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testUpdateBook() {
        BookRequestDTO request = new BookRequestDTO("Original Title", orwellId, 2020, "978-4444444444");
        ResponseEntity<BookResponseDTO> createResponse = restTemplate.postForEntity(
                "/api/books", request, BookResponseDTO.class);

        Long id = createResponse.getBody().getId();

        BookRequestDTO updated = new BookRequestDTO("Updated Title", orwellId, 2020, "978-4444444444");

        // exchange is the more general method that lets us specify the HTTP method explicitly
        // HttpEntity — wraps the request body (and optionally headers)
        ResponseEntity<BookResponseDTO> updateResponse = restTemplate.exchange(
                "/api/books/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                BookResponseDTO.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Updated Title", updateResponse.getBody().getTitle());

        ResponseEntity<BookResponseDTO> getResponse = restTemplate.getForEntity(
                "/api/books/" + id, BookResponseDTO.class);
        assertEquals("Updated Title", getResponse.getBody().getTitle());
    }

    @Test
    void testDeleteBook() {
        BookRequestDTO request = new BookRequestDTO("To Be Deleted", orwellId, 2020, "978-5555555555");
        ResponseEntity<BookResponseDTO> createResponse = restTemplate.postForEntity(
                "/api/books", request, BookResponseDTO.class);

        Long id = createResponse.getBody().getId();

        restTemplate.delete("/api/books/" + id);

        // don't deserialize the JSON - just gimme the raw string
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "/api/books/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}