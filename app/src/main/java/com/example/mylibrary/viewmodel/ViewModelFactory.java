package com.example.mylibrary.viewmodel;

import com.example.mylibrary.persistence.repositories.BookRepository;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.BookRepositoryInterface;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import com.example.mylibrary.ui.home.HomeViewModel;
import com.example.mylibrary.ui.profile.ProfileViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory INSTANCE;
    private final UserRepositoryInterface _userRepository;
    private final BookRepositoryInterface _bookRepository;

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
        _bookRepository = new BookRepository(context);
    }


    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(_userRepository);
        }
        if(modelClass.isAssignableFrom(UserListViewModel.class)) {
            UserListViewModel viewModel = new UserListViewModel(_userRepository);
            return modelClass.cast(viewModel);
        }
        if(modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(_userRepository);
        }
        if(modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(_bookRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModelClass");
    }

}
