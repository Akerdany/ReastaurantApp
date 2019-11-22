package com.example.reastaurantapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder> {

    ArrayList<Food> Items;

    public MenuRecyclerAdapter(ArrayList<Food> items) {
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
        holder.ItemName.setText(Items.get(position).getItemName());
        holder.ItemPrice.setText(Items.get(position).getItemPrice());
        holder.ItemDesc.setText(Items.get(position).getItemDesc());
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

        TextView ItemName;
        TextView ItemPrice;
        TextView ItemDesc;

        ImageView ItemImage;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.ItemName);
            ItemPrice = itemView.findViewById(R.id.ItemPrice);
            ItemDesc = itemView.findViewById(R.id.ItemDesc);

            ItemImage = itemView.findViewById(R.id.ItemImage);
        }
    }


}
