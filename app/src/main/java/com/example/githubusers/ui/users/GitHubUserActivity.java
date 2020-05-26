package com.example.githubusers.ui.users;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.githubusers.GithubApplication;
import com.example.githubusers.R;
import com.example.githubusers.databinding.ActivityMainBinding;
import com.example.githubusers.di.AppModule;
import com.example.githubusers.di.DaggerAppComponent;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class GitHubUserActivity extends AppCompatActivity {
    @Inject
    UserViewModelProvider userViewModelProvider;
    @Inject
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ((GithubApplication)getApplication()).appComponent.inject(this);
        //setting up recyclerview
        mActivityMainBinding.recyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        mActivityMainBinding.recyclerViewId.setHasFixedSize(true);
        mActivityMainBinding.recyclerViewId.setAdapter(adapter);
        ProgressDialog progressDialog = new ProgressDialog(this);
        userViewModelProvider.getUserPagedList().observe(this, pagedList -> {
            adapter.submitList(pagedList);
            progressDialog.dismiss();
        });
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }
}
