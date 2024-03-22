package com.example.mylibrary.classes;

import androidx.annotation.NonNull;

public class Book {
    private String title;
    private Author author;
    private String isbn;
    private String publisher;
    private Boolean isPossessed = false;
    private String comment;
    private int rating;


    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
        this.author.AddBook(this);
    }

    public void UpdateBook(String title, Author author) {
        this.title = title;
        if(this.author != author) {
            this.author.RemoveBook(this);
            this.author = author;
            this.author.AddBook(this);
        }
    }

    public void AddComment(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public void SetISBN(String isbn) {
        this.isbn = isbn;
    }

    public void SetPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void SetRating(int rating) {
        this.rating = rating;
    }

    public void SetPossessed(Boolean isPossessed) {
        this.isPossessed = isPossessed;
    }

    public String GetTitle() {
        return title;
    }

    public Author GetAuthor() {
        return author;
    }

    public Book GetBook() {
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
