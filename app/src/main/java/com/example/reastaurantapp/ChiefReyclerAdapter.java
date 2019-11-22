package com.example.reastaurantapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChiefReyclerAdapter extends RecyclerView.Adapter<ChiefReyclerAdapter.ChiefViewHolder> {

    ArrayList<Order> Orders;

    public ChiefReyclerAdapter(ArrayList<Order> orders) {
        Orders = orders;
    }

    @NonNull
    @Override
    public ChiefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chief_item, parent, false);
        return new ChiefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiefViewHolder holder, int position) {
        holder.OrderNO.setText(Orders.get(position).getOrderNumber());
        ArrayList<Food> Items = Orders.get(position).getItems();
        String Desc = "";
        for (int i = 0; i < Items.size(); i++){
            Desc += Items.get(i).getItemName() + "/n";
        }
        holder.OrderDesc.setText(Desc);
    }

    @Override
    public int getItemCount() {
        if (Orders == null){
            return 0;
        }else {
            return Orders.size();
        }
    }

    public class ChiefViewHolder extends RecyclerView.ViewHolder {

        TextView OrderNO;
        TextView OrderDesc;

        public ChiefViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNO = itemView.findViewById(R.id.OrderNO);
            OrderDesc = itemView.findViewById(R.id.OrderDesc);
        }
    }
}
