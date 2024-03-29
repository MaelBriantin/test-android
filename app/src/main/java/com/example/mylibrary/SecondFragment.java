package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mylibrary.models.User;
import com.example.mylibrary.databinding.FragmentSecondBinding;
import com.example.mylibrary.persistence.AppDatabase;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.repositories.UserRepositoryInterface;

import java.util.List;
import java.util.concurrent.Executors;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private User selectedUser;
    private MainActivityUserList mainActivityUserList;
    private Long createdUser;
    private String userEmoticon;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityUserList = (MainActivityUserList) getActivity();

        selectedUser = new User("", "", "", null);

        Spinner emoticonSpinner = view.findViewById(R.id.emoticon_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.emoticons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emoticonSpinner.setAdapter(adapter);

        emoticonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userEmoticon = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        binding.saveButton.setOnClickListener(v -> {
            selectedUser.setName(binding.newUserName.getText().toString());
            selectedUser.setEmail(binding.newUserEmail.getText().toString());
            selectedUser.setPassword(binding.newUserPassword.getText().toString());
            selectedUser.setAvatar(userEmoticon);

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = AppDatabase.getDatabase(requireContext());

                UserRepositoryInterface userRepository = new UserRepository(getContext());

                Long userId = userRepository.insertUser(selectedUser).get(0);

                Intent intent = new Intent(getActivity(), MainActivity.class);

                intent.putExtra("selectedUserId", userId);
                startActivity(intent);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}