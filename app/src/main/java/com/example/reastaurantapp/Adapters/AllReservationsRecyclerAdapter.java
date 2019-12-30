package com.example.reastaurantapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Reservation;
import com.example.reastaurantapp.R;
import com.example.reastaurantapp.ReservationEditDelete;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AllReservationsRecyclerAdapter extends FirestoreRecyclerAdapter<Reservation, AllReservationsRecyclerAdapter.AllReservationViewHolder> {

    public AllReservationsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Reservation> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllReservationViewHolder holder, int position, @NonNull Reservation model) {
        holder.ReservationID.setText(model.getID());
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

        mHolder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ReservationEditDelete.class);

                intent.putExtra("ReservationID", mHolder.ReservationID.getText().toString());

                parent.getContext().startActivity(intent);
            }
        });
        return mHolder;
    }

    class AllReservationViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout main_layout;

        TextView ReservationID, TableName, ReservationYear, ReservationMonth, ReservationDay, ReservationHour;

        public AllReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            main_layout = itemView.findViewById(R.id.main_layout);

            ReservationID = itemView.findViewById(R.id.reservationID);
            TableName = itemView.findViewById(R.id.reservationTable);
            ReservationYear = itemView.findViewById(R.id.reservationYearEdit);
            ReservationMonth = itemView.findViewById(R.id.reservationMonth);
            ReservationDay = itemView.findViewById(R.id.reservationDay);
            ReservationHour = itemView.findViewById(R.id.reservationHour);
        }
    }
}
