package com.example.githubusers.repository;

import android.app.Application;
import android.util.Log;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClient;
import com.example.githubusers.data.User;
import com.example.githubusers.db.UserDao;
import com.example.githubusers.db.UserDatabase;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private UserDao userdao;
    private CompositeDisposable compositeDisposable;
    private final Observable<PagedList<User>> userList;

    public UserRepository(Application application) {
        this.userList = new RxPagedListBuilder<>(
                userdao.getUsers(), 50)
                .buildObservable();
        UserDatabase db = UserDatabase.getDatabase(application);
        userdao = db.userDao();
        compositeDisposable = new CompositeDisposable();
    }

    public void loadUsers(MutableLiveData<List<User>> listMutableLiveData) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApi()
                .getUsers(listMutableLiveData.getValue().size())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    listMutableLiveData.setValue(users);
                    //saveUsers(users);
                }));
    }

   /* private void saveUsers(List<User> users) {

        userdao.deleteAll(users)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, integer + " Github users deleted from User table.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError", e);
                    }
                });*/

        /*userdao.insert(users)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Github users inserted into User table.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError", e);
                    }
                });
    }*/

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}