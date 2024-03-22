package com.example.mylibrary.classes;

import android.text.EmojiConsistency;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String email;
    private String password;
    private EmojiConsistency avatar;
    private List<Book> library;

    public User(String username, String email, String password, EmojiConsistency avatar, Book[] library) {
        this.username = username;
        this.email = email;
        this.password = password;
        if (library != null) {
            this.library = Arrays.asList(library);
        }
    }

    public void AddBook(Book book) {
        library.add(book);
    }

    public void RemoveBook(Book book) {
        library.remove(book);
    }

    public List<Book> GetLibrary() {
        return library;
    }

    public String GetName() {
        return username;
    }

    public String GetEmail() {
        return email;
    }

    public String GetPassword() {
        return password;
    }
}

