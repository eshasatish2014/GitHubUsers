package com.example.githubusers.di;

import android.app.Application;

import com.example.githubusers.GithubApplication;
import com.example.githubusers.db.UserDao;
import com.example.githubusers.db.UserDatabase;
import com.example.githubusers.repository.UserDataSourceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    UserDao providesUserDao(Application application) {
        UserDatabase userDatabase = UserDatabase.getDatabase(application);
        return userDatabase.userDao();
    }
}
