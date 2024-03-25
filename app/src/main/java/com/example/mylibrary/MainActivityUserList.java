package com.example.mylibrary;

import android.os.Bundle;

import com.example.mylibrary.classes.User;
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

public class MainActivityUserList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainUserListBinding binding;

    private List<User> users;

    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        users = new ArrayList<>();
//        User user1 = new User("user1", "", "", null, null);
//        User user2 = new User("user2", "", "", null, null);
//        User user3 = new User("user3", "", "", null, null);
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        ListView usersListView = findViewById(R.id.list_view);
//
//        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
//        usersListView.setAdapter(adapter);

//        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedUser = users.get(position);
//                Log.i("user", selectedUser.GetName());
//            }
//        });
    }
}