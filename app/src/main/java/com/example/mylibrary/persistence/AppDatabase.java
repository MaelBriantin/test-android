package com.example.mylibrary.persistence;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mylibrary.models.Book;
import com.example.mylibrary.models.User;
import com.example.mylibrary.persistence.dao.BookDao;
import com.example.mylibrary.persistence.dao.UserDao;

@Database(entities = {User.class, Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "library_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
    public abstract BookDao bookDao();

}
