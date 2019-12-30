package com.example.reastaurantapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reastaurantapp.Adapters.AllReservationsRecyclerAdapter;
import com.example.reastaurantapp.Classes.Reservation;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AllReservationsFragment extends Fragment {

    private AllReservationsRecyclerAdapter allReservationsRecyclerAdapter;

    public AllReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_all_reservations, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.AllReservationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        CollectionReference ref = FirebaseFirestore.getInstance().collection("reservation");

        FirestoreRecyclerOptions<Reservation> options = new FirestoreRecyclerOptions.Builder<Reservation>()
                .setQuery(ref, Reservation.class)
                .build();

        allReservationsRecyclerAdapter = new AllReservationsRecyclerAdapter(options);
        recyclerView.setAdapter(allReservationsRecyclerAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (allReservationsRecyclerAdapter != null){
            allReservationsRecyclerAdapter.startListening();
        }else {
            Log.d(TAG, "onStart: adapter error");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (allReservationsRecyclerAdapter != null){
            allReservationsRecyclerAdapter.stopListening();
        }else {
            Log.d(TAG, "onStop: adapter error");
        }
    }
}
