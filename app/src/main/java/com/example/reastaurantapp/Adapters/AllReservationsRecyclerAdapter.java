package com.example.reastaurantapp.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Reservation;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AllReservationsRecyclerAdapter extends FirestoreRecyclerAdapter<Reservation, AllReservationsRecyclerAdapter.AllReservationViewHolder> {

    public AllReservationsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllReservationViewHolder holder, int position, @NonNull Reservation model) {

    }

    @NonNull
    @Override
    public AllReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class AllReservationViewHolder extends RecyclerView.ViewHolder{

        public AllReservationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
