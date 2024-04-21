package com.example.mylibrary.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mylibrary.models.User;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import io.reactivex.Maybe;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final UserRepositoryInterface userRepository;

    public MainActivityViewModel(UserRepositoryInterface userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public Maybe<User> loadUser(Long id) {
       return userRepository.getUserById(id).map(user -> {
            currentUser.postValue(user);
            Log.i("DATA", "Success");
            return user;
        });
    }
}
