package com.example.githubusers.di;

import com.example.githubusers.GithubApplication;
import com.example.githubusers.ui.users.GitHubUserActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = {}, modules = {AppModule.class, RoomModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(GitHubUserActivity mainActivity);

}
