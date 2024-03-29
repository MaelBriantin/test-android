package com.example.mylibrary.persistence.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.AppDatabase;
import com.example.mylibrary.persistence.dao.UserDao;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import java.util.List;

import io.reactivex.Flowable;


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

    public User getUserById(int id) {
        return userDao.getById(id);
    }

    public void insertUser(User user) {
        userDao.insertAll(user);
    }
    public void insertAll(User... users) {
        userDao.insertAll(users);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
