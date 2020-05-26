package com.example.githubusers.repository;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.data.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserDataSource extends PageKeyedDataSource<Integer, User> {
    private static final int since = 0;
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 30;
    private CompositeDisposable compositeDisposable;
    private GitHubUserService gitHubUserService;

    @Inject
    UserDataSource(CompositeDisposable compositeDisposable, GitHubUserService gitHubUserService) {
        this.compositeDisposable = compositeDisposable;
        this.gitHubUserService = gitHubUserService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, User> callback) {
        compositeDisposable.add(gitHubUserService
                .getUsers(since)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    callback.onResult(users, null, FIRST_PAGE);
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
        compositeDisposable.add(gitHubUserService
                .getUsers(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(users, key);
                }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {
        compositeDisposable.add(gitHubUserService
                .getUsers(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    Integer key = !users.isEmpty() ? params.key + 1 : null;
                    callback.onResult(users, key);
                }));
    }
}