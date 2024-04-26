package com.example.mylibrary.persistence.repositories;

import android.content.Context;

import com.example.mylibrary.models.Book;
import com.example.mylibrary.persistence.AppDatabase;
import com.example.mylibrary.persistence.dao.BookDao;
import com.example.mylibrary.repositories.BookRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class BookRepository implements BookRepositoryInterface {

    private final BookDao bookDao;

    public BookRepository(Context context) {
        this.bookDao =  AppDatabase
                .getDatabase(context)
                .bookDao();
    }
    public Flowable<List<Book>> getAllUserBooks(int id) {
        return bookDao.getAllUserBooks(id);
    }

    public Single<Long> insertBook(Book book, int userId) {
        return bookDao.insertBook(book.getTitle(), book.getAuthor(), book.getDescription(), userId);
    }
}
