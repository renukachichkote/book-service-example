package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping("book/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Book postBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping("/books")
    public List<Book> fetchBooks() {
        return bookService.fetchBooks();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> fetchBookById(@PathVariable("id") Long bookId) {
        Book book = bookService.fetchBookById(bookId);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/books/{id}")
    public String deleteBookById(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);
        return "Book deleted Successfully!!";
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable("id") Long bookId,
                           @RequestBody Book book) {
        return bookService.updateBook(bookId,book);
    }

}
