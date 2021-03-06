package com.galvanize.tmo.paspringstarter.domain.service;

import com.galvanize.tmo.paspringstarter.domain.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private static List<Book> library = new ArrayList<>();

    private static Integer bookID = 1;

    public static List<Book> getLibrary() {
        return library;
    }

    public static void addBook(Book newBook) {
        library.add(newBook);
    }

    public static void removeAllBooks() {
        library.clear();
    }

    public static Integer generateBookID() {
        return bookID++;
    }

}
