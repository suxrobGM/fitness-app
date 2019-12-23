package com.example.fitnessapp.context;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "app_database.sqlite";
    public static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
        }

        return instance;
    }

    public abstract UserDao getUserDao();
}
