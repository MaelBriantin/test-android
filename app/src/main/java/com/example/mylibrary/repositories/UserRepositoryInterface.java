package com.example.mylibrary.repositories;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface UserRepositoryInterface {
    public Flowable<List<User>> getAllUsers();
    public Maybe<User> getUserById(Long id);
    public Single<Long> insertUser(User user);
    public void insertAll(User... users);
    public Completable deleteUser(User user);
    public Completable updateUser(User user);
}
