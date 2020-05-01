package com.example.githubusers.repository;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClient;
import com.example.githubusers.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    private MutableLiveData<List<User>> mMutableLiveData = new MutableLiveData<>();

    public LiveData<List<User>> loadUsers() {
        GitHubUserService gitHubUserService = RetrofitClient.getRetrofitInstance().create(GitHubUserService.class);

        Disposable disposable = gitHubUserService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> mMutableLiveData.setValue(users));
        return mMutableLiveData;
    }
}
