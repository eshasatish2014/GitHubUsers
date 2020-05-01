package com.example.githubusers.api;

import com.example.githubusers.data.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GitHubUserService {
    @GET("users")
    Observable<List<User>> getUsers();
}
