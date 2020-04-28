package com.example.githubusers.ui;

import android.app.Application;

import com.example.githubusers.data.Users;
import com.example.githubusers.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModelProvider extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<Users> users;

    public UserViewModelProvider(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        users = userRepository.loadUsers();
    }

    public LiveData<Users> getUsers() {
        return users;
    }
}
