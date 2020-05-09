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
    //private LiveData<List<User>> users;
    //private UserRepository userRepository;
    private LiveData<PagedList<User>> userPagedList;
    private UserDataSourceFactory userDataSourceFactory;

    public UserViewModelProvider(@NonNull Application application) {
        super(application);
        //users = new MutableLiveData<>();
        //userRepository = new UserRepository(application);
        //userRepository.loadUsers((MutableLiveData<List<User>>) users);
        userDataSourceFactory = new UserDataSourceFactory();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(UserDataSource.PAGE_SIZE).build();
        userPagedList = (new LivePagedListBuilder<Integer,User>(userDataSourceFactory, config)).build();
    }

    /*LiveData<List<User>> getUsers() {
        return users;
    }*/

    public LiveData<PagedList<User>> getUserPagedList() {
        return userPagedList;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        userDataSourceFactory.getCompositeDisposable().dispose();
    }
}