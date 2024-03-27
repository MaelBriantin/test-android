package com.example.mylibrary.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mylibrary.models.User;
import com.example.mylibrary.models.UserWithLibrary;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithLibrary> getUsersWithLibrary();

    @Query("SELECT * FROM user WHERE id = :id")
    User getById(int id);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
