package com.example.githubusers.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubusers.R;
import com.example.githubusers.data.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {

    private List<User> users;
    private Context context;

    UserAdapter(Context context) {
        this.context = context;
        this.users = new ArrayList<>();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView txtTitle;
        private ImageView imageView;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.loginId);
            imageView = mView.findViewById(R.id.imageViewId);
        }
    }

    @Override
    public @NonNull CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gituser_row_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(users.get(position).getLogin());
        Glide.with(context).load(users.get(position).getAvatar_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    void setUsers(List<User> users) {
        if (users != null) {
            this.users = users;
            notifyDataSetChanged();
        }
    }
}