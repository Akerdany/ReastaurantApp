package com.example.reastaurantapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "TAG";

    String tablename;
    int Day, Month, Year, Hour, Minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    Dialog myDialog;
    TextView close;
    TextView selectedTable;
    ImageView image;
    Map<String, Object> Reservation;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore databaseConnection;
    BottomNavigationView bottomNav;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    //TODO: Add the other menu cases buttons
                    switch (item.getItemId()) {
                        case R.id.nav_home_icon:
                            selectedFragment = new GR();
                            break;
                        case R.id.nav_more_icon:
                            selectedFragment = new MorePage();
                            break;
                        case R.id.nav_menu_icon:
                            selectedFragment = new MenuFragment();
                            break;

                        default:
                            selectedFragment = new GR();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage_client);
        databaseConnection = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        getUserType();

        bottomNav = findViewById(R.id.homepage_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement,
                new GR()).commit();

        myDialog = new Dialog(this);
        Reservation = new HashMap<>();
    }

    public void ShowPopup(View view) {
        tablename = view.getTag().toString();
        myDialog.setContentView(R.layout.activity_popupmenu);
        image = myDialog.findViewById(R.id.viewImage);
        selectedTable = myDialog.findViewById(R.id.hh);
        switch (view.getId()) {
            case R.id.tablefour1:
            case R.id.tablefour2:
            case R.id.tablefour3:
            case R.id.tablefour4:
            case R.id.tablefour5:
            case R.id.tablefour6:
                image.setImageResource(R.drawable.table4);
                selectedTable.setText("You Selected a 4 Person Table.");
                break;
            case R.id.tabletwo1:
            case R.id.tabletwo2:
            case R.id.tabletwo3:
            case R.id.tabletwo4:
                image.setImageResource(R.drawable.table2);
                selectedTable.setText("You Selected a 2 Person Table.");
                break;
            case R.id.tablefive1:
            case R.id.tablefive2:
                image.setImageResource(R.drawable.table5);
                selectedTable.setText("You Selected a 5 Person Table.");
                break;
        }
        close = myDialog.findViewById(R.id.txtclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void chooseDate(View view) {
        Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datediag = new DatePickerDialog(HomePageActivity.this, HomePageActivity.this, Year, Month, Day);
        datediag.show();
        myDialog.dismiss();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;
        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        Minute = c.get(Calendar.MINUTE);
        TimePickerDialog timediag = new TimePickerDialog(HomePageActivity.this, HomePageActivity.this, Hour, Minute, DateFormat.is24HourFormat(this));
        timediag.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        Reservation.put("Minute", minuteFinal);
        Reservation.put("Hour", hourFinal);
        Reservation.put("Day", dayFinal);
        Reservation.put("Month", monthFinal);
        Reservation.put("Year", yearFinal);
        Reservation.put("tableName", tablename);

        databaseConnection.collection("reservation").document().set(Reservation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomePageActivity.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void getUserType(){
//        if (firebaseAuth.getCurrentUser() == null) {
//
//        }
        String userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference docRef = databaseConnection.collection("users").document(userID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
