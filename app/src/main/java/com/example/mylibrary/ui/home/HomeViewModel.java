package com.example.mylibrary.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mylibrary.models.Book;
import com.example.mylibrary.models.User;
import com.example.mylibrary.repositories.BookRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList<Book>> allUserBooks = new MutableLiveData<>();
    private final BookRepositoryInterface bookRepository;

    public List<Book> books;

    public HomeViewModel(BookRepositoryInterface bookRepository) {
        super();
        this.bookRepository = bookRepository;
        mText = new MutableLiveData<>();
        mText.setValue("Library");
    }

    public Flowable<ArrayList<Book>> loadUserBooks(int userId) {
        return bookRepository.getAllUserBooks(userId).map(books -> {
            allUserBooks.postValue(new ArrayList<>(books));
            return new ArrayList<>(books);
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}