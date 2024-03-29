package com.example.mylibrary.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mylibrary.models.User;
import com.example.mylibrary.models.UserWithLibrary;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithLibrary> getUsersWithLibrary();

    @Query("SELECT * FROM user WHERE id = :id")
    User getById(int id);
    @Query("SELECT * FROM user WHERE id = :id")
    User getById(Long id);

    @Insert
    List<Long> insertAll(User... users);

    @Insert
    Single<Long> insert(User user);

    @Delete
    void delete(User user);
}
