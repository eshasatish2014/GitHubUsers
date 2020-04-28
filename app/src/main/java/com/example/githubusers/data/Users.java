package com.example.githubusers.data;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private float total_count;
    private boolean incomplete_results;
    List<Item> items = new ArrayList<>();

    public float getTotal_count() {
        return total_count;
    }

    public void setTotal_count(float total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
