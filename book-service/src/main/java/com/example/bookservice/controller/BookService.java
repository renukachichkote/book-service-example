package com.example.bookservice.controller;

import com.example.bookservice.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> fetchBooks() {
        return bookRepository.findAll();
    }

    public Book fetchBookById(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book updateBook(Long bookId, Book book) {
        Book book1 = bookRepository.findById(bookId).get();

        if (book.getAuthor() != null) {
            book1.setAuthor(book.getAuthor());
        }
        if (book.getTitle() != null) {
            book1.setTitle(book.getTitle());
        }
        return bookRepository.save(book1);
    }
}
