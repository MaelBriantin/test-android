package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;
import com.example.mylibrary.models.User;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mylibrary.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public Long userId;
    public User user;
    public UserRepositoryInterface userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Executors.newSingleThreadExecutor().execute(() -> {
            Intent intent = getIntent();
            userId = (Long) intent.getLongExtra("selectedUserId", 0);
            user = userRepository.getUserById(userId);
//        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_author, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}