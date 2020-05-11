package com.example.githubusers.repository;

import android.app.Application;

import com.example.githubusers.api.RetrofitClient;
import com.example.githubusers.data.User;
import com.example.githubusers.db.UserDao;
import com.example.githubusers.db.UserDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    private UserDao userdao;
    private final LiveData<PagedList<User>> userPagedList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final int PAGE_SIZE = 30;

    //Network Only Pagination
    public UserRepository() {
        UserDataSourceFactory userDataSourceFactory = new UserDataSourceFactory();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build();
        userPagedList = (new LivePagedListBuilder<>(userDataSourceFactory, config)).build();
        compositeDisposable = userDataSourceFactory.getCompositeDisposable();
    }

    //Database + Network Pagination
    //Network data is paged into the database, database is paged into UI
    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userdao = db.userDao();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE).build();

        userPagedList = new LivePagedListBuilder<>(
                userdao.getUsers(), config).setBoundaryCallback(createBoundaryCallback()).build();
    }

    //Trigger network call when DataSource runs out of data
    private PagedList.BoundaryCallback<User> createBoundaryCallback() {
        return new PagedList.BoundaryCallback<User>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                compositeDisposable.add(RetrofitClient.getInstance()
                        .getApi()
                        .getUsers(0)
                        .subscribeOn(Schedulers.io())
                        .subscribe(userdao::insert));
            }

            @Override
            public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
                super.onItemAtEndLoaded(itemAtEnd);
                compositeDisposable.add(RetrofitClient.getInstance()
                        .getApi()
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