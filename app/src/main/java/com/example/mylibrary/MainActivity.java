package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import com.example.mylibrary.viewmodel.MainActivityViewModel;
import com.example.mylibrary.viewmodel.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mylibrary.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public MainActivityViewModel viewModel = ViewModelFactory.getInstance(this).create(MainActivityViewModel.class);
    private ActivityMainBinding binding;
    public UserRepositoryInterface userRepository;
    public CompositeDisposable _mDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_author, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        userRepository = new UserRepository(this);

        Intent intent = getIntent();
        Long userId =  Objects.requireNonNull(intent.getExtras()).getLong("userId");

        _mDisposable.add( viewModel.loadUser(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                            Log.i("DATA", "Success");
                        }, throwable -> {
                            Log.i("DATA", "Error");
                        })
        );
    }

}