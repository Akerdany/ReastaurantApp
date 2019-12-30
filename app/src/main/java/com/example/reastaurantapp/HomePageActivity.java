package com.example.reastaurantapp;

import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.reastaurantapp.Classes.Reservation;
import com.example.reastaurantapp.Classes.Tables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "TAG";
    String tablename;

    int Day, Month, Year, Hour, Minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    boolean reserved = false;

    Reservation tempReservation = new Reservation();

    Dialog myDialog;
    TextView close;
    TextView selectedTable, smoking, window;
    ArrayList <Tables> ar;
    Tables table;
    ImageView image;
    Map<String, Object> Reservation;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore databaseConnection;
    BottomNavigationView bottomNav;

    private int userType = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;
                    //TODO: Add the other menu cases buttons
                    switch (item.getItemId()) {
                        case R.id.nav_home_icon_client:
                            selectedFragment = new branches();
                            break;

                        case R.id.nav_chef_home_icon:
                            selectedFragment = new ChefFragment();
                            break;

                        case R.id.nav_more_icon:
                            selectedFragment = new MorePage();
                            break;

                        case R.id.nav_menu_icon:
                        case R.id.nav_menu_icon_chef:
                        case R.id.nav_menu_icon_client:
                            selectedFragment = new MenuFragment();
                            break;

                        case R.id.nav_admin_icon:
                            selectedFragment = new AdminPanel();
                            break;

                        case R.id.nav_allReservation_icon:
                            selectedFragment = new AllReservationsFragment();
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage_client);
        databaseConnection = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        Intent previous_intent = getIntent();
        if (previous_intent.getStringExtra("userType") != null) {

            userType = Integer.parseInt(previous_intent.getStringExtra("userType"));

            Log.w(TAG, "The userType: " + userType);
//            Toast.makeText(HomePageActivity.this, "UserType: " + userType,
//                    Toast.LENGTH_SHORT).show();
        }

        bottomNav = findViewById(R.id.homepage_bottom_navigation);

        switch (userType)
        {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement,
                        new AdminPanel()).commit();
                bottomNav.getMenu().clear();
                bottomNav.inflateMenu(R.menu.admin_bottom_navigation);
                break;

            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement,
                        new ChefFragment()).commit();
                bottomNav.getMenu().clear();
                bottomNav.inflateMenu(R.menu.chef_bottom_navigation);
                break;

            case 2:
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement,
                        new branches()).commit();
                bottomNav.getMenu().clear();
                bottomNav.inflateMenu(R.menu.client_bottom_navigation);
                break;
        }

        bottomNav.setOnNavigationItemSelectedListener(navigationListener);

        myDialog = new Dialog(this);
        Reservation = new HashMap<>();
    }

    public void ShowPopup(View view)
    {
        tablename = view.getTag().toString();
        myDialog.setContentView(R.layout.activity_popupmenu);
        image = myDialog.findViewById(R.id.viewImage);
        selectedTable = myDialog.findViewById(R.id.hh);

        ar = new ArrayList<>();
        table = new Tables();

        switch (view.getId())
        {
            case R.id.tablefour1:
                getDetails("tablefour1");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tablefour2:
                getDetails("tablefour2");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tablefour3:
                getDetails("tablefour3");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tablefour4:
                getDetails("tablefour4");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tablefour5:
                getDetails("tablefour5");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tablefour6:
                getDetails("tablefour6");
                image.setImageResource(R.drawable.table4);
                selectedTable.setText(getText(R.string.four_people_table));
                break;

            case R.id.tabletwo1:
                getDetails("tabletwo1");
                image.setImageResource(R.drawable.table2);
                selectedTable.setText(getText(R.string.two_people_table));
                break;

            case R.id.tabletwo2:
                getDetails("tabletwo2");
                image.setImageResource(R.drawable.table2);
                selectedTable.setText(getText(R.string.two_people_table));
                break;

            case R.id.tabletwo3:
                getDetails("tabletwo3");
                image.setImageResource(R.drawable.table2);
                selectedTable.setText(getText(R.string.two_people_table));
                break;

            case R.id.tabletwo4:
                getDetails("tabletwo4");
                image.setImageResource(R.drawable.table2);
                selectedTable.setText(getText(R.string.two_people_table));
                break;

            case R.id.tablefive1:
                getDetails("tablefive1");
                image.setImageResource(R.drawable.table5);
                selectedTable.setText(getText(R.string.five_people_table));
                break;

            case R.id.tablefive2:
                getDetails("tablefive2");
                image.setImageResource(R.drawable.table5);
                selectedTable.setText(getText(R.string.five_people_table));
                break;
        }
        close = myDialog.findViewById(R.id.txtclose);
        close.setOnClickListener(v -> myDialog.dismiss());
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void chooseDate(View view)
    {
        Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datediag = DatePickerDialog.newInstance(HomePageActivity.this, Year, Month, Day);
        datediag.show(getSupportFragmentManager(), "DatePicker");
        myDialog.dismiss();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth)
    {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;
        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        Minute = c.get(Calendar.MINUTE);
        TimePickerDialog timediag = TimePickerDialog.newInstance(HomePageActivity.this, Hour, Minute, DateFormat.is24HourFormat(this));
        timediag.show(getSupportFragmentManager(), "TimePicker");
        timediag.enableMinutes(false);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second)
    {
        hourFinal = hourOfDay;
        FirebaseFirestore firebaseFirestore;
        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference docRef = firebaseFirestore.collection("reservation");

        reserved = false;

        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    tempReservation = snapshot.toObject(Reservation.class);

                    if (tempReservation.getDay().equals(String.valueOf(dayFinal)) &&
                            tempReservation.getHour().equals(String.valueOf(hourFinal)) &&
                            tempReservation.getMonth().equals(String.valueOf(monthFinal)) &&
                            tempReservation.getYear().equals(String.valueOf(yearFinal)) &&
                            tempReservation.getTablename().equals(String.valueOf(tablename)))
                    {
                        reserved = true;
                        Toast.makeText(HomePageActivity.this, "Table is already reserved in this time, Please try another time.", Toast.LENGTH_LONG).show();
                        break;

                    }
                }

                if (!reserved){

                    DocumentReference documentReference = databaseConnection.collection("reservation").document();

                    final String documentID = documentReference.getId();

                    tempReservation.setID(documentID);
                    tempReservation.setYear(String.valueOf(yearFinal));
                    tempReservation.setMonth(String.valueOf(monthFinal));
                    tempReservation.setDay(String.valueOf(dayFinal));
                    tempReservation.setHour(String.valueOf(hourFinal));
                    tempReservation.setTablename(tablename);

                    documentReference.set(tempReservation).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                finish();
                                Intent intent = new Intent(HomePageActivity.this, ReservationMenu.class);
                                intent.putExtra("ReservationID", documentID);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(HomePageActivity.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
//        docRef.get().addOnSuccessListener(documentSnapshot ->
//        {
//            if (documentSnapshot.exists())
//            {
//                com.example.reastaurantapp.Classes.Reservation r = documentSnapshot.toObject(Reservation.class);
//                System.out.println(r.getDay());
//                Toast.makeText(HomePageActivity.this, r.getDay(), Toast.LENGTH_LONG).show();
//            }
//            else
//            {
//                //Toast.makeText(HomePageActivity.this, "hh", Toast.LENGTH_LONG).show();
//            }
//        });






    public void getdir(View view)
    {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=30.087352, 31.334681"));
        startActivity(intent);
    }

    public void panels(View view)
    {
        switch (view.getId())
        {
            case R.id.userspanel:
                Intent myIntent = new Intent(HomePageActivity.this, UsersPanel.class);
                HomePageActivity.this.startActivity(myIntent);
                break;

            case R.id.foodpanel:
                Intent intent = new Intent(this, FoodPanel.class);
                startActivity(intent);
                break;

            case R.id.tablespanel:
                Intent intent1 = new Intent(this, EditTables.class);
                startActivity(intent1);
                break;
        }
    }

    public void getDetails(String ID)
    {
        FirebaseFirestore firebaseFirestore;
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firebaseFirestore.collection("tables").document(ID);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists())
            {
                smoking = myDialog.findViewById(R.id.smoking);
                window = myDialog.findViewById(R.id.window);
                smoking.setText(documentSnapshot.getString("smokingtype"));
                window.setText(documentSnapshot.getString("windowtype"));
                if(documentSnapshot.getString("smokingtype") == "Smoking")
                {
                    smoking.setTextColor(Color.RED);
                }
            }
        });
    }
}
