package com.example.mylibrary.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mylibrary.models.User;
import com.example.mylibrary.models.UserWithLibrary;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Transaction
    @Query("SELECT * FROM user")
    List<UserWithLibrary> getUsersWithLibrary();

    @Query("SELECT * FROM user WHERE id = :id")
    Maybe<User> getById(Long id);

    @Insert
    List<Long> insertAll(User... users);

    @Insert
    Single<Long> insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);
}
