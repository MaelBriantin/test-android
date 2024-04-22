package com.example.mylibrary.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mylibrary.models.User;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class UserListViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<User>> allUsers = new MutableLiveData<>();
    private final UserRepositoryInterface userRepository;

    public UserListViewModel(UserRepositoryInterface userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public MutableLiveData<ArrayList<User>> getAllUsers() {
        return allUsers;
    }

    public Flowable<ArrayList<User>> loadAllUsers() {
        return userRepository.getAllUsers().map(users -> {
            allUsers.postValue(new ArrayList<>(users));
            return new ArrayList<>(users);
        });
    }

}

