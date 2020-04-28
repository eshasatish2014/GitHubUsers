package com.example.githubusers.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubusers.R;
import com.example.githubusers.data.Users;
import com.example.githubusers.api.GitHubUserService;
import com.example.githubusers.api.RetrofitClientInstance;

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
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        userViewModelProvider = ViewModelProviders.of(this).get(UserViewModelProvider.class);
        userViewModelProvider.getUsers().observe(this, new Observer<Users>() {
            @Override
            public void onChanged(Users users) {
                adapter.setUsers(users);
                progressDoalog.dismiss();
            }
        });
        generateUserList(userViewModelProvider.getUsers().getValue());
    }


    private void generateUserList(Users users) {
        recyclerView = findViewById(R.id.recyclerViewId);
        adapter = new UserAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
