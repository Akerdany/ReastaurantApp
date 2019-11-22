package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ChiefActivity extends AppCompatActivity {

    RecyclerView ChiefRecyclerView;
    ChiefReyclerAdapter chiefReyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Order> Orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief);

        ChiefRecyclerView = findViewById(R.id.ChiefRecyclerView);
        chiefReyclerAdapter = new ChiefReyclerAdapter(Orders);
        layoutManager = new GridLayoutManager(this, 2);

        ChiefRecyclerView.setAdapter(chiefReyclerAdapter);
        ChiefRecyclerView.setLayoutManager(layoutManager);
    }
}
