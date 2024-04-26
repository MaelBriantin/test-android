package com.example.mylibrary.persistence.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.mylibrary.models.Book;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book WHERE userId = :id")
    Flowable<List<Book>> getAllUserBooks(int id);

    @Query("INSERT INTO book (userId, title, author, description) VALUES (:userId, :title, :author, :description)")
    Single<Long> insertBook(String title, String author, String description, int userId);
}
