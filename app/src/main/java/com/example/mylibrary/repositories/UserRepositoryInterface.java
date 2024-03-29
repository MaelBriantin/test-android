package com.example.mylibrary.repositories;

import com.example.mylibrary.models.User;

import java.util.List;

import io.reactivex.Flowable;

public interface UserRepositoryInterface {
    public Flowable<List<User>> getAllUsers();
    public User getUserById(int id);
    public User getUserById(Long id);
    public List<Long> insertUser(User user);
    public void insertAll(User... users);
    public void deleteUser(User user);
}
