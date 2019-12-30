package com.example.reastaurantapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Reservation;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

        mHolder.deleteReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference reservationDocRef = FirebaseFirestore.getInstance().collection("reservation").document(mHolder.ReservationID.getText().toString());
                DocumentReference foodReservationDocRef = FirebaseFirestore.getInstance().collection("reservationfood").document(mHolder.ReservationID.getText().toString());

//                showProgressbar();

                reservationDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        foodReservationDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                hideProgressBar();
                                Toast.makeText(parent.getContext(), "Reservation Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                hideProgressBar();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        hideProgressBar();
                        Toast.makeText(parent.getContext(), "Error in Deleting Reservation", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return mHolder;
    }

    class AllReservationViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout main_layout;

        TextView ReservationID, TableName, ReservationYear, ReservationMonth, ReservationDay, ReservationHour;

        Button deleteReservationBtn;

        public AllReservationViewHolder(@NonNull View itemView) {
            super(itemView);

            main_layout = itemView.findViewById(R.id.main_layout);

            ReservationID = itemView.findViewById(R.id.reservationID);
            TableName = itemView.findViewById(R.id.reservationTable);
            ReservationYear = itemView.findViewById(R.id.reservationYearEdit);
            ReservationMonth = itemView.findViewById(R.id.reservationMonth);
            ReservationDay = itemView.findViewById(R.id.reservationDay);
            ReservationHour = itemView.findViewById(R.id.reservationHour);

            deleteReservationBtn = itemView.findViewById(R.id.deleteReservationBtn);
        }
    }
}
