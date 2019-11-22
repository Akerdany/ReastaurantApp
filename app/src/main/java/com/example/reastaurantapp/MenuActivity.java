package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    RecyclerView MenuRecyclerView;
    MenuRecyclerAdapter menuRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Food> Items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MenuRecyclerView = findViewById(R.id.MenuRecyclerView);
        menuRecyclerAdapter = new MenuRecyclerAdapter(Items);
        layoutManager = new LinearLayoutManager(this);

        MenuRecyclerView.setAdapter(menuRecyclerAdapter);
        MenuRecyclerView.setLayoutManager(layoutManager);
    }
}
