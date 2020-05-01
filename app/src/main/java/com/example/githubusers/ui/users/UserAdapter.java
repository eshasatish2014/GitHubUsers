package com.example.githubusers.ui.users;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.githubusers.R;
import com.example.githubusers.data.User;
import com.example.githubusers.databinding.GituserRowItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;

    UserAdapter() {
        this.users = new ArrayList<>();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private GituserRowItemBinding rowItemBinding;

        UserViewHolder(@NonNull GituserRowItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }

    @Override
    public @NonNull
    UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GituserRowItemBinding rowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.gituser_row_item, parent, false);
        return new UserViewHolder(rowItemBinding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.rowItemBinding.setUser(users.get(position));
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