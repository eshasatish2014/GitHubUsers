package com.example.githubusers.repository;


import com.example.githubusers.data.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.disposables.CompositeDisposable;

public class UserDataSourceFactory extends DataSource.Factory<Integer,User> {

    private MutableLiveData<PageKeyedDataSource<Integer, User>> userLiveDataSource = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        UserDataSource userDataSource = new UserDataSource(compositeDisposable);
        userLiveDataSource.postValue(userDataSource);
        return userDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, User>> getUserLiveDataSource() {
        return userLiveDataSource;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
