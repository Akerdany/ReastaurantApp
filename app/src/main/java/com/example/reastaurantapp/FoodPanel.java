package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.reastaurantapp.Adapters.FoodPanelRecyclerAdapter;
import com.example.reastaurantapp.Classes.Food;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FoodPanel extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FoodPanelRecyclerAdapter foodPanelRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_panel);

        RecyclerView recyclerView = findViewById(R.id.FoodPanelRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection("food");

        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(ref, Food.class)
                .build();

        foodPanelRecyclerAdapter = new FoodPanelRecyclerAdapter(options);
        recyclerView.setAdapter(foodPanelRecyclerAdapter);
    }

    public void AddNewFood(View view) {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (foodPanelRecyclerAdapter != null){
            foodPanelRecyclerAdapter.startListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (foodPanelRecyclerAdapter != null){
            foodPanelRecyclerAdapter.stopListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }
}
