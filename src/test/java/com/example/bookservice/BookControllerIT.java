package com.example.bookservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {BookServiceApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(ContainersConfig.class)
public class BookControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Order(1)
    @Test
    @DisplayName("connect to db and print all the available books")
    void init() throws Exception {
        bookRepository.findAll().forEach(System.out::println);
    }

    @Order(2)
    @Test
    @DisplayName("fetch a book having id 2")
    void fetchBook_bookExists_success() throws Exception {
        // Given data
        Integer id = 2;
        String url = "/byId/" + id;

        // fetch book
        ResponseEntity<Book> responseEntity = restTemplate.getForEntity(url, Book.class);
        Book book = responseEntity.getBody();
        System.out.println(book);
    }

    @Order(3)
    @Test
    @DisplayName("fetch all books")
    void fetchAllBooks_bookExists_success() throws Exception {
        // Given data
        String url = "/all";

        // fetch all books
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<Book>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>(){});
        List<Book> books = response.getBody();

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(books);
        assertEquals(5, books.size());
        System.out.println(books);
    }

    @Order(4)
    @Test
    @DisplayName("Delete a book having id 10")
    void deleteBook_bookExists_success() throws Exception {
        // Given data
        Integer id = 5;
        String url = "/book/" + id;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, id);
        // Verify
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(bookRepository.findById(id).orElse(null));
    }
}
