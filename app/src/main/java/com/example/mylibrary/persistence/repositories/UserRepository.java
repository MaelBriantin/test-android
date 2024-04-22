package com.example.mylibrary.persistence.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.AppDatabase;
import com.example.mylibrary.persistence.dao.UserDao;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;


public class UserRepository implements UserRepositoryInterface {
    private final UserDao userDao;


    public UserRepository(Context context) {
        this.userDao =  AppDatabase
                .getDatabase(context)
                .userDao();
    }

    public Flowable<List<User>> getAllUsers() {
        return userDao.getAll();
    }

    public Maybe<User> getUserById(Long id) {
        return userDao.getById(id);
    }

    public Single<Long> insertUser(User user) {
        return userDao.insert(user);

    }
    public void insertAll(User... users) {
        userDao.insertAll(users);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
