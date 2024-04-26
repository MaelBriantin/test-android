package com.example.mylibrary.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mylibrary.MainActivity;
import com.example.mylibrary.MainActivityUserList;
import com.example.mylibrary.R;
import com.example.mylibrary.databinding.FragmentHomeBinding;
import com.example.mylibrary.models.Book;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.repositories.BookRepository;
import com.example.mylibrary.persistence.repositories.UserRepository;
import com.example.mylibrary.viewmodel.MainActivityViewModel;
import com.example.mylibrary.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Book> userBooks = new ArrayList<>();
    private MainActivity mainActivity;
    private HomeViewModel homeViewModel;
    private ArrayAdapter<Book> adapter;
    private AlertDialog.Builder builder;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelFactory.getInstance(getContext()).create(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.bookListView;
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, userBooks);

        mainActivity = (MainActivity) getActivity();



        binding.newBookButton.setOnClickListener(v -> {
            showAddBook();
        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Add New Book");
//
//        View dialogView = getLayoutInflater().inflate(R.layout.add_book_dialog_view, null);

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

        return root;
    }

    private void showAddBook() {
        builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Book");

        View dialogView = getLayoutInflater().inflate(R.layout.add_book_dialog_view, null);
        builder.setView(dialogView);

        EditText bookTitle = dialogView.findViewById(R.id.book_dialog_title);
        EditText bookGenre = dialogView.findViewById(R.id.book_dialog_genre);
        EditText bookDescription = dialogView.findViewById(R.id.book_dialog_description);
        Spinner bookAuthor = dialogView.findViewById(R.id.book_dialog_author_spinner);

        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Author 1", "Author 2", "Author 3"});
        bookAuthor.setAdapter(authorAdapter);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Book newBook = new Book(
                        bookTitle.getText().toString(),
                        bookAuthor.getSelectedItem().toString(),
                        bookGenre.getText().toString(),
                        bookDescription.getText().toString()
                );
                compositeDisposable.add(homeViewModel.insertBook(newBook, Objects.requireNonNull(mainActivity.viewModel.getCurrentUser().getValue()).getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        bookId -> {
//                                    newBook.setId((int) (long) bookId);
//                                    userBooks.add(newBook);
                                            adapter.notifyDataSetChanged();
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

        builder.show();

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}