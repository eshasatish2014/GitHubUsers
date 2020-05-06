package com.example.githubusers.ui.users;

import android.app.Application;

import com.example.githubusers.data.User;
import com.example.githubusers.repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModelProvider extends AndroidViewModel {
    private LiveData<List<User>> users;
    private UserRepository userRepository;

    public UserViewModelProvider(@NonNull Application application) {
        super(application);
        users = new MutableLiveData<>();
        userRepository = new UserRepository(application);
        userRepository.loadUsers((MutableLiveData<List<User>>) users);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        userRepository.getCompositeDisposable().dispose();
    }
}