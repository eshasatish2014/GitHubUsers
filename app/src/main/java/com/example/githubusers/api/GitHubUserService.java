package com.example.githubusers.api;

import com.example.githubusers.data.Users;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubUserService {
    @GET("users?q=language:java")
    Call<Users> getUsers();
}
