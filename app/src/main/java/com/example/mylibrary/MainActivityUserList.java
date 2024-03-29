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

import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityUserList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainUserListBinding binding;

    private UserRepositoryInterface userRepository;

    private User selectedUser;

    public
    ArrayList<User> users = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected  void onStart() {
        super.onStart();
        userRepository = new UserRepository(this);
        compositeDisposable.add(userRepository.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(test -> {
                    users.addAll( test);
                    ListView usersListView = findViewById(R.id.list_view);
                    ((ArrayAdapter) usersListView.getAdapter().notifyDataSetChanged());
                })
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

            User user1 = new User("user1", "", "", "😀");
            User user2 = new User("user2", "", "", "😂");
            User user3 = new User("user3", "", "", "😁");



            //users = userRepository.getAllUsers();


        binding = ActivityMainUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }
}