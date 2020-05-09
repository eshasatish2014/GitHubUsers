package com.example.githubusers.ui.users;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.githubusers.R;
import com.example.githubusers.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDoalog;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //setting up recyclerview
        mActivityMainBinding.recyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        mActivityMainBinding.recyclerViewId.setHasFixedSize(true);

        UserViewModelProvider userViewModelProvider = new ViewModelProvider(this).get(UserViewModelProvider.class);
        UserAdapter adapter = new UserAdapter();

        userViewModelProvider.getUserPagedList().observe(this, pagedList -> {
            adapter.submitList(pagedList);
            progressDoalog.dismiss();
        });
        mActivityMainBinding.recyclerViewId.setAdapter(adapter);
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }
}
