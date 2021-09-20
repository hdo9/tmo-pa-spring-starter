package com.galvanize.tmo.paspringstarter.presentation.controller;

import com.galvanize.tmo.paspringstarter.domain.entity.Book;
import com.galvanize.tmo.paspringstarter.domain.service.LibraryService;
import com.galvanize.tmo.paspringstarter.presentation.dto.BookRequest;
import com.galvanize.tmo.paspringstarter.presentation.dto.LibraryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class LibraryController {

    @GetMapping("/health")
    public void health() {

    }

    @DeleteMapping("/api/books")
    public ResponseEntity<Object> removeAllBooks() {
        LibraryService.removeAllBooks();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/api/books")
    public ResponseEntity<Object> getAllSortedBooks() {
        List<Book> sortedBooks = LibraryService.getLibrary().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
        LibraryResponse response = LibraryResponse.builder()
                .books(sortedBooks)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/api/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addBooks(@RequestBody BookRequest bookRequest) {
        Book newBook = Book.builder()
                .id(UUID.randomUUID().toString())
                .author(bookRequest.getAuthor())
                .title(bookRequest.getTitle())
                .yearPublished(bookRequest.getYearPublished())
                .build();
        LibraryService.addBook(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }
}
