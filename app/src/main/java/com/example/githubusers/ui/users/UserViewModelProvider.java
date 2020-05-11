package com.example.githubusers.ui.users;

import android.app.Application;

import com.example.githubusers.data.User;
import com.example.githubusers.repository.UserDataSource;
import com.example.githubusers.repository.UserDataSourceFactory;
import com.example.githubusers.repository.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;

public class UserViewModelProvider extends AndroidViewModel {
    private LiveData<PagedList<User>> userPagedList;
    private UserRepository userRepository;

    public UserViewModelProvider(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        userPagedList = userRepository.getUserPagedList();
    }

    public LiveData<PagedList<User>> getUserPagedList() {
        return userPagedList;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        userRepository.getCompositeDisposable().dispose();
    }
}