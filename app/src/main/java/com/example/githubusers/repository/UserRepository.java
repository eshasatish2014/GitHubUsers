package com.example.githubusers.repository;

import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClientInstance;
import com.example.githubusers.data.Users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    MutableLiveData<Users> users = new MutableLiveData<>();
    public LiveData<Users> loadUsers() {
        GitHubUserService gitHubUserService = RetrofitClientInstance.getRetrofitInstance().create(GitHubUserService.class);
        Call<Users> call = gitHubUserService.getUsers();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                users.setValue(response.body());
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
            }
        });
      return users;
    }
}
