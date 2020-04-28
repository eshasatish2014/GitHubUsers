package com.example.githubusers.api;

import com.example.githubusers.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubUserService {
    @GET("users")
    Call<List<User>> getUsers();
}
