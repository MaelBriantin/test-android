package com.example.mylibrary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mylibrary.MainActivity;
import com.example.mylibrary.databinding.FragmentHomeBinding;
import com.example.mylibrary.models.Book;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.repositories.BookRepository;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.viewmodel.MainActivityViewModel;
import com.example.mylibrary.viewmodel.ViewModelFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Book> userBooks = new ArrayList<>();
    private MainActivityViewModel viewModel;

    private MainActivity mainActivity;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelFactory.getInstance(getContext()).create(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.bookListView;
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, userBooks);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;
        mainActivity.viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user ->{
            compositeDisposable.add(homeViewModel.loadUserBooks(user.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bookList -> {
                        userBooks.clear();
                        userBooks.addAll(bookList);
                        listView.setAdapter(adapter);
                    })
            );
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}