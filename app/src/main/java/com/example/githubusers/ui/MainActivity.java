package com.example.githubusers.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubusers.R;
import com.example.githubusers.data.User;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ImageView mImageView;
    private RecyclerView recyclerView;
    private UserViewModelProvider userViewModelProvider;
    private ProgressDialog progressDoalog;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userViewModelProvider = ViewModelProviders.of(this).get(UserViewModelProvider.class);
        userViewModelProvider.getUsers().observe(this, users -> {
            adapter.setUsers(users);
            progressDoalog.dismiss();
        });
        generateUserList(userViewModelProvider.getUsers().getValue());
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
    }

    private void generateUserList(List<User> users) {
        recyclerView = findViewById(R.id.recyclerViewId);
        adapter = new UserAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
