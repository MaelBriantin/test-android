package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import java.util.ArrayList;

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = users.get(position);

                Intent intent = new Intent(view.getContext(), MainActivity.class);

                intent.putExtra("selectedUserId", (long) selectedUser.getId());

                startActivity(intent);
            }
        });
    }
    @Override
    protected  void onStart() {
        super.onStart();
        userRepository = new UserRepository(this);
        compositeDisposable.add(userRepository.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userList -> {
                    users.addAll(userList);
                    ListView usersListView = findViewById(R.id.list_view);
                    ((ArrayAdapter) usersListView.getAdapter()).notifyDataSetChanged();
                })
        );
    }
}