package com.example.mylibrary.viewmodel;

import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory INSTANCE;
    private final UserRepositoryInterface _userRepository;

    public static ViewModelFactory getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if(INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(context);
                }
            }
        }
        return INSTANCE;
    }
    private ViewModelFactory(Context context) {
        _userRepository = new UserRepository(context);
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(_userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModelClass");
    }
}
