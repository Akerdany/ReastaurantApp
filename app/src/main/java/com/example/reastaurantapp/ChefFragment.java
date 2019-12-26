package com.example.reastaurantapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reastaurantapp.Adapters.ChefRecyclerAdapter;
import com.example.reastaurantapp.Classes.Order;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChefFragment extends Fragment {

    private ChefRecyclerAdapter chefRecyclerAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChefFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chef, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.ChiefRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2));

        CollectionReference ref = db.collection("reservationfood");

        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(ref, Order.class)
                .build();

        chefRecyclerAdapter = new ChefRecyclerAdapter(options);
        recyclerView.setAdapter(chefRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (chefRecyclerAdapter != null){
            chefRecyclerAdapter.startListening();
        }else {
            Log.d(TAG, "onStart: adapter error");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (chefRecyclerAdapter != null){
            chefRecyclerAdapter.stopListening();
        }else {
            Log.d(TAG, "onStop: adapter error");
        }
    }
}
