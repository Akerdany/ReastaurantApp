package com.example.reastaurantapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Adapters.MenuRecyclerAdapter;
import com.example.reastaurantapp.Classes.Food;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MenuFragment extends Fragment {

    private View rootView;

    private RecyclerView MenuRecyclerView;
    private MenuRecyclerAdapter menuRecyclerAdapter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref;

    public MenuFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        MenuRecyclerView = rootView.findViewById(R.id.MenuRecyclerView);
        MenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = db.collection("food");

        FirestoreRecyclerOptions<Food> options = new FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(ref, Food.class)
                .build();

        menuRecyclerAdapter = new MenuRecyclerAdapter(options);
        MenuRecyclerView.setAdapter(menuRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (menuRecyclerAdapter != null){
            menuRecyclerAdapter.startListening();
        }else {
            Log.d(TAG, "onStart: adapter error");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (menuRecyclerAdapter != null){
            menuRecyclerAdapter.stopListening();
        }else {
            Log.d(TAG, "onStop: adapter error");
        }

    }
}
