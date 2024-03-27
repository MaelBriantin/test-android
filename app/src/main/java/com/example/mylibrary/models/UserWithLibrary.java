package com.example.mylibrary.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithLibrary {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userId"
    )
    private List<Book> books;

    public UserWithLibrary(User user, List<Book> books) {
        this.user = user;
        this.books = books;
    }

    public User getUser() {
        return user;
    }

    public List<Book> getLibrary() {
        return books;
    }
}
