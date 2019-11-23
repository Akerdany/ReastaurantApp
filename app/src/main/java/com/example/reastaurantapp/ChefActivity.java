package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reastaurantapp.Adapters.ChefReyclerAdapter;
import com.example.reastaurantapp.Classes.Order;

import java.util.ArrayList;

public class ChefActivity extends AppCompatActivity {

    RecyclerView ChiefRecyclerView;
    ChefReyclerAdapter chefReyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Order> Orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        ChiefRecyclerView = findViewById(R.id.ChiefRecyclerView);
        chefReyclerAdapter = new ChefReyclerAdapter(Orders);
        layoutManager = new GridLayoutManager(this, 2);

        ChiefRecyclerView.setAdapter(chefReyclerAdapter);
        ChiefRecyclerView.setLayoutManager(layoutManager);
    }
}
