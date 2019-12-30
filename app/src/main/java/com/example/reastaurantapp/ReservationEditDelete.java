package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reastaurantapp.Classes.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReservationEditDelete extends AppCompatActivity {

    String ID;
    
    ConstraintLayout main_layout;

    TextView reservationTable;
    EditText reservationYear, reservationMonth, reservationDay, reservationHour;
    
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_edit_delete);

        ID = getIntent().getStringExtra("ReservationID");
        
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.AllReservationProgressBar);

        reservationTable = findViewById(R.id.reservationTable);
        reservationYear = findViewById(R.id.reservationYearEdit);
        reservationMonth = findViewById(R.id.reservationMonthEdt);
        reservationDay = findViewById(R.id.reservationDayEdt);
        reservationHour = findViewById(R.id.reservationHourEdt);

        InitializeViews();
    }

    public void InitializeViews(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("reservation").document(ID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Reservation item = documentSnapshot.toObject(Reservation.class);

                reservationTable.setText(item.getTablename());
                reservationYear.setText(item.getYear());
                reservationMonth.setText(item.getMonth());
                reservationDay.setText(item.getDay());
                reservationHour.setText(item.getHour());
            }
        });
    }

    public void DeleteReservation(View view) {
        DocumentReference reservationDocRef = FirebaseFirestore.getInstance().collection("reservation").document(ID);
        DocumentReference foodReservationDocRef = FirebaseFirestore.getInstance().collection("reservationfood").document(ID);

        showProgressbar();

        reservationDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                foodReservationDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressBar();
                        Toast.makeText(ReservationEditDelete.this, "Reservation Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressBar();
                Toast.makeText(ReservationEditDelete.this, "Error in Deleting Reservation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showProgressbar(){
        main_layout.setAlpha((float) 0.2);
        progressBar.setVisibility(View.VISIBLE);

    }

    public void hideProgressBar(){
        main_layout.setAlpha((float) 1.0);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
