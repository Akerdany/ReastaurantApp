package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.reastaurantapp.Adapters.UsersRecyclerAdapter;
import com.example.reastaurantapp.Classes.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersPanel extends AppCompatActivity {

    UsersRecyclerAdapter usersRecyclerAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_panel);

        RecyclerView recyclerView = findViewById(R.id.UsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference ref = db.collection("users");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(ref, User.class)
                .build();

        usersRecyclerAdapter = new UsersRecyclerAdapter(options);
        recyclerView.setAdapter(usersRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (usersRecyclerAdapter != null){
            usersRecyclerAdapter.startListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (usersRecyclerAdapter != null){
            usersRecyclerAdapter.stopListening();
        }else {
            Toast.makeText(this, "Error in Getting Food Menu", Toast.LENGTH_SHORT).show();
        }
    }
}
