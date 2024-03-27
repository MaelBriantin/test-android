package com.example.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mylibrary.databinding.ActivityMainUserListBinding;
import com.example.mylibrary.databinding.FragmentFirstBinding;
import com.example.mylibrary.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private List<User> users;
    private User selectedUser;

    private MainActivityUserList mainActivityUserList;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityUserList = (MainActivityUserList) getActivity();

        users = mainActivityUserList.users;

        ListView usersListView = view.findViewById(R.id.list_view);

        ArrayAdapter<User> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, users);
        usersListView.setAdapter(adapter);
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = users.get(position);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("selectedUserId", selectedUser.getId());
                startActivity(intent);
            }
        });

        binding.newUserButton.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}