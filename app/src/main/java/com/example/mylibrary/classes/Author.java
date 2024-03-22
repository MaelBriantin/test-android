package com.example.mylibrary.classes;

import java.util.List;

public class Author {
    private String firstname;
    private String lastname;
    private List<Book> books;

    public Author(String firstname, String lastname, List<Book> books) {
        this.firstname = firstname;
        this.lastname = lastname;
        if (books != null) {
            this.books = books;
        }
    }

    public String GetFullName() {
        return firstname + " " + lastname;
    }

    public List<Book> GetBooks() {
        return books;
    }

    public void SetBooks(List<Book> books) {
        this.books = books;
    }

    public void AddBook(Book book) {
        books.add(book);
    }

    public void RemoveBook(Book book) {
        books.remove(book);
    }
}
