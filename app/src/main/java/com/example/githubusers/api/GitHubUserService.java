package com.example.githubusers.api;

import com.example.githubusers.data.User;

import java.util.List;

import androidx.paging.PagedList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubUserService {
    @GET("users")
    Observable<List<User>> getUsers(@Query("since") int since);
}
