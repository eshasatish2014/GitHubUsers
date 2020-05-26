package com.example.githubusers;

import android.app.Application;

import com.example.githubusers.di.AppComponent;
import com.example.githubusers.di.AppModule;
import com.example.githubusers.di.DaggerAppComponent;

// appComponent lives in the Application class to share its lifecycle
public class GithubApplication extends Application {

    // Reference to the application graph that is used across the whole app
    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
