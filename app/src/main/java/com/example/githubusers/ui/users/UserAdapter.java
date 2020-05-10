package com.example.githubusers.ui.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.githubusers.R;
import com.example.githubusers.data.User;
import com.example.githubusers.databinding.GituserRowItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends PagedListAdapter<User,UserAdapter.UserViewHolder> {

    protected UserAdapter() {
        super(DIFF_CALLBACK);
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
        holder.rowItemBinding.setUser(getItem(position));
    }

    private static DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(User oldUser, User newUser) {
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(User oldUser,
                                                  User newUser) {
                    return oldUser.equals(newUser);
                }
            };
}