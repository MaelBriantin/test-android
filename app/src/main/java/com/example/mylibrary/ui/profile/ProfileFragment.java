package com.example.mylibrary.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mylibrary.MainActivity;
import com.example.mylibrary.MainActivityUserList;
import com.example.mylibrary.R;
import com.example.mylibrary.models.User;
import com.example.mylibrary.databinding.FragmentProfileBinding;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private MainActivity mainActivity;
    private Intent intent;

    private static final Class MainActivityUserList = MainActivityUserList.class;

    private User userProfile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        userProfile = mainActivity.user;

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        Spinner spinner = binding.emoticonSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainActivity, R.array.emoticons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        User user = mainActivity.user;

        binding.newUserName.setText(userProfile.getName());
        binding.newUserEmail.setText(userProfile.getEmail());
        binding.newUserPassword.setText(userProfile.getPassword());
        binding.emoticonSpinner.setSelection(adapter.getPosition(userProfile.getAvatar()));
        Button saveButton = binding.saveButton;
        Button deleteButton = binding.deleteAccountButton;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfile.setName(binding.newUserName.getText().toString());
                userProfile.setEmail(binding.newUserEmail.getText().toString());
                userProfile.setPassword(binding.newUserPassword.getText().toString());
                userProfile.setAvatar(spinner.getSelectedItem().toString());
                mainActivity.user = userProfile;
                mainActivity._mDisposable.add(mainActivity.userRepository.updateUser(userProfile)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Snackbar.make(binding.getRoot(), "User updated", Snackbar.LENGTH_SHORT).show();
                        }));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity._mDisposable.add(mainActivity.userRepository.deleteUser(userProfile)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(() -> {
//                            Snackbar.make(binding.getRoot(), "User deleted", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(mainActivity, MainActivityUserList);
                            startActivity(intent);
                        }));
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}