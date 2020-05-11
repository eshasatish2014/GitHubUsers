package com.example.githubusers.db;

import com.example.githubusers.data.User;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Insert
    void insert(List<User> user);

    @Delete
    void deleteAll(List<User> user);

    @Query("SELECT * FROM User")
    public  DataSource.Factory<Integer, User> getUsers();

}
