package com.example.mylibrary.ui.profile;

import androidx.lifecycle.ViewModel;

import com.example.mylibrary.models.User;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import io.reactivex.Maybe;

public class ProfileViewModel extends ViewModel {

    private final UserRepositoryInterface userRepository;


    public ProfileViewModel(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public Maybe<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }
}
