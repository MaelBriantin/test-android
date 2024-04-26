package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;
import com.example.mylibrary.viewmodel.MainActivityViewModel;
import com.example.mylibrary.viewmodel.UserListViewModel;
import com.example.mylibrary.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityUserList extends AppCompatActivity {

    private ActivityMainUserListBinding binding;

    private UserListViewModel viewModel;

    private UserRepositoryInterface userRepository;

    private User selectedUser;

    public ArrayList<User> users = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelFactory.getInstance(this).create(UserListViewModel.class);

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = users.get(position);
                Intent intent = new Intent(view.getContext(), MainActivity.class);

                long userId = (long) selectedUser.getId();

                intent.putExtra("userId", (long) userId);
                startActivity(intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New User");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_view, null);

        EditText newUserName = dialogView.findViewById(R.id.dialog_name);
        EditText newUserEmail = dialogView.findViewById(R.id.dialog_email);
        EditText newUserPassword = dialogView.findViewById(R.id.dialog_password);
        Spinner emoticonSpinner = dialogView.findViewById(R.id.dialog_emoticon_spinner);

        ArrayAdapter<CharSequence> emoticonAdapter = ArrayAdapter.createFromResource(this, R.array.emoticons, android.R.layout.simple_spinner_item);
        emoticonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emoticonSpinner.setAdapter(emoticonAdapter);

        builder.setView(dialogView);

        binding.newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                User user = new User(
                        newUserName.getText().toString(),
                        newUserEmail.getText().toString(),
                        newUserPassword.getText().toString(),
                        emoticonSpinner.getSelectedItem().toString()
                );
                compositeDisposable.add(viewModel.insertUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user_id -> {
                                    Intent intent = new Intent(MainActivityUserList.this, MainActivity.class);
                                    intent.putExtra("userId", user_id);
                                    startActivity(intent);
                                    newUserName.setText("");
                                    newUserEmail.setText("");
                                    newUserPassword.setText("");
                                }
                        )
                );
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
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
                    users.clear();
                    users.addAll(userList);
                    ListView usersListView = binding.listView;
                    ((ArrayAdapter) usersListView.getAdapter()).notifyDataSetChanged();
                })
        );
    }
}