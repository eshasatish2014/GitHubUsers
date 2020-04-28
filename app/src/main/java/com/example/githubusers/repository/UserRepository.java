package com.example.githubusers.repository;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClient;
import com.example.githubusers.data.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    public LiveData<List<User>> loadUsers() {
        GitHubUserService gitHubUserService = RetrofitClient.getRetrofitInstance().create(GitHubUserService.class);
        Call<List<User>> call = gitHubUserService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                users.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
            }
        });
        return users;
    }
}
