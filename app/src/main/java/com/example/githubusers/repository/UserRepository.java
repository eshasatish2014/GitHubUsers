package com.example.githubusers.repository;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClient;
import com.example.githubusers.data.User;
import com.example.githubusers.db.UserDao;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    private CompositeDisposable compositeDisposable;
    private final LiveData<PagedList<User>> userPagedList;
    public static final int PAGE_SIZE = 30;

    //Network Only Pagination
    //@Inject
    public UserRepository(UserDataSourceFactory userDataSourceFactory) {
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build();
        userPagedList = (new LivePagedListBuilder<>(userDataSourceFactory, config)).build();
        compositeDisposable = userDataSourceFactory.getCompositeDisposable();
    }

    //Database + Network Pagination
    //Network data is paged into the database, database is paged into UI
    @Inject
    public UserRepository(UserDao userdao, PagedList.Config config,CompositeDisposable compositeDisposable,GitHubUserService gitHubUserService) {
        this.compositeDisposable = compositeDisposable;
        userPagedList = new LivePagedListBuilder<>(
                userdao.getUsers(), config).setBoundaryCallback(createBoundaryCallback(userdao,compositeDisposable,gitHubUserService)).build();
    }

    //Trigger network call when DataSource runs out of data
    private PagedList.BoundaryCallback<User> createBoundaryCallback(UserDao userdao,CompositeDisposable compositeDisposable,GitHubUserService gitHubUserService) {
        return new PagedList.BoundaryCallback<User>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                compositeDisposable.add(gitHubUserService
                        .getUsers(0)
                        .subscribeOn(Schedulers.io())
                        .subscribe(userdao::insert));
            }

            @Override
            public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                compositeDisposable.add(gitHubUserService
                        .getUsers(itemAtEnd.getId())
                        .subscribeOn(Schedulers.io())
                        .subscribe(userdao::insert));
            }
        };
    }

    public LiveData<PagedList<User>> getUserPagedList() {
        return userPagedList;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}