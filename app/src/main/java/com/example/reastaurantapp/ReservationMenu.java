package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reastaurantapp.Adapters.ReservationMenuRecyclerAdapter;
import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.Classes.Order;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReservationMenu extends AppCompatActivity {

    public static ArrayList<Food> mFood = new ArrayList<>();

    public static int itemsCount = 0;

    public static TextView CartNoTxt;

    private RecyclerView ReservationMenuRecyclerView;
    private ReservationMenuRecyclerAdapter reservationMenuRecyclerAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_menu);

        CartNoTxt = findViewById(R.id.CartNoTxt);

        ReservationMenuRecyclerView = findViewById(R.id.ReservationMenuRecyclerView);
        ReservationMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection("food");

        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(ref, Food.class)
                .build();

        reservationMenuRecyclerAdapter = new ReservationMenuRecyclerAdapter(options);
        ReservationMenuRecyclerView.setAdapter(reservationMenuRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (reservationMenuRecyclerAdapter != null){
            reservationMenuRecyclerAdapter.startListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (reservationMenuRecyclerAdapter != null){
            reservationMenuRecyclerAdapter.stopListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveReservationMenu(View view) {
        Order order = new Order(getIntent().getStringExtra("ReservationID"), mFood);

        db.collection("reservationfood").document(order.getOrderNumber()).set(order)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        Toast.makeText(ReservationMenu.this, "order saved", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
