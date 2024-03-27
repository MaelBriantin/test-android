package com.example.mylibrary.persistence.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.mylibrary.models.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book")
    List<Book> getAll();
}
