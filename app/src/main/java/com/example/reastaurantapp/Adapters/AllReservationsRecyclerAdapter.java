package com.example.reastaurantapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Reservation;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AllReservationsRecyclerAdapter extends FirestoreRecyclerAdapter<Reservation, AllReservationsRecyclerAdapter.AllReservationViewHolder> {

    public AllReservationsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllReservationViewHolder holder, int position, @NonNull Reservation model) {
        holder.TableName.setText(model.getTablename());
        holder.ReservationYear.setText(model.getYear());
        holder.ReservationMonth.setText(model.getMonth());
        holder.ReservationDay.setText(model.getDay());
        holder.ReservationHour.setText(model.getHour());
    }

    @NonNull
    @Override
    public AllReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_reservation_item, parent, false);
        AllReservationViewHolder mHolder = new AllReservationViewHolder(view);
        return mHolder;
    }

    class AllReservationViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout main_layout;

        TextView TableName, ReservationYear, ReservationMonth, ReservationDay, ReservationHour;

        public AllReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            main_layout = itemView.findViewById(R.id.main_layout);

            TableName = itemView.findViewById(R.id.reservationTable);
            ReservationYear = itemView.findViewById(R.id.reservationYear);
            ReservationMonth = itemView.findViewById(R.id.reservationMonth);
            ReservationDay = itemView.findViewById(R.id.reservationDay);
            ReservationHour = itemView.findViewById(R.id.reservationHour);
        }
    }
}
