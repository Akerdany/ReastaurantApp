package com.example.reastaurantapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder> {

    ArrayList<MenuItem> Items;

    public MenuRecyclerAdapter(ArrayList<MenuItem> items) {
        Items = items;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (Items == null){
            return 0;
        }else {
            return Items.size();
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
