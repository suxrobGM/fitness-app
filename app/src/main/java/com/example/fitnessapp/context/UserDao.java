package com.example.fitnessapp.context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Update
    void update(User user);

    @Insert
    void insert(User users);

    @Delete
    void delete(User user);
}
