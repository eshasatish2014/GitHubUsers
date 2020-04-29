package com.example.githubusers.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.githubusers.R;
import com.example.githubusers.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDoalog;
    private UserAdapter adapter;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        UserViewModelProvider userViewModelProvider = ViewModelProviders.of(this).get(UserViewModelProvider.class);
        userViewModelProvider.getUsers().observe(this, users -> {
            adapter.setUsers(users);
            progressDoalog.dismiss();
        });
        generateUserList();
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }

    private void generateUserList() {
        mActivityMainBinding.recyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter();
        mActivityMainBinding.recyclerViewId.setAdapter(adapter);
    }
}
