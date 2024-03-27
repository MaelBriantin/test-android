package com.example.mylibrary;

import android.os.Bundle;

import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.AppDatabase;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivityUserList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainUserListBinding binding;

    private UserRepositoryInterface userRepository;

    private User selectedUser;

    public List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(this);

            User user1 = new User("user1", "", "", "😀");
            User user2 = new User("user2", "", "", "😂");
            User user3 = new User("user3", "", "", "😁");

            userRepository = new UserRepository(db.userDao());

            //userRepository.insertAll(user1, user2, user3);

            users = userRepository.getAllUsers();
        });

        binding = ActivityMainUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }
}