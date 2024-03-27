package com.example.mylibrary.repositories;

import com.example.mylibrary.models.User;

import java.util.List;

public interface UserRepositoryInterface {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void insertUser(User user);
    public void insertAll(User... users);
    public void deleteUser(User user);
}
