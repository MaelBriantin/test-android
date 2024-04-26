package com.example.mylibrary.repositories;

import com.example.mylibrary.models.Book;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public interface BookRepositoryInterface {
    public Flowable<List<Book>> getAllUserBooks(int id);
}
