package com.example.fitnessapp.context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public float weight;

    @ColumnInfo
    public float height;

    @ColumnInfo
    public int age;

    @ColumnInfo
    public String photoPath;

    @ColumnInfo
    public String gender;

    @ColumnInfo
    public String email;

    @ColumnInfo
    public String username;

    @ColumnInfo
    public String password;
}
