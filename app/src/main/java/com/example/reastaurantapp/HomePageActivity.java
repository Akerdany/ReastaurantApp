package com.example.reastaurantapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
                        case R.id.nav_menu_icon_client:
                            selectedFragment = new MenuFragment();
                            break;
                        case R.id.nav_navigation_icon_client:
                            selectedFragment = new GoogleMapsDirections();
                            break;
                        case R.id.nav_admin_icon:
                            selectedFragment = new AdminPanel();
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
            Toast.makeText(HomePageActivity.this, "UserType: " + userType,
                    Toast.LENGTH_SHORT).show();
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
        switch (view.getId())
        {
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
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void chooseDate(View view)
    {
        Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datediag = new DatePickerDialog(HomePageActivity.this, R.style.popuptheme, HomePageActivity.this, Year, Month, Day);
        datediag.show();
        myDialog.dismiss();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;
        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR_OF_DAY);
        Minute = c.get(Calendar.MINUTE);
        TimePickerDialog timediag = new TimePickerDialog(HomePageActivity.this, R.style.popuptheme, HomePageActivity.this, Hour, Minute, DateFormat.is24HourFormat(this));
        timediag.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        Reservation.put("Minute", minuteFinal);
        Reservation.put("Hour", hourFinal);
        Reservation.put("Day", dayFinal);
        Reservation.put("Month", monthFinal);
        Reservation.put("Year", yearFinal);
        Reservation.put("tableName", tablename);

        DocumentReference documentReference = databaseConnection.collection("reservation").document();

        final String documentID = documentReference.getId();

        documentReference.set(Reservation).addOnCompleteListener(this, new OnCompleteListener<Void>()
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
//                myIntent.putExtra("key", value); //Optional parameters
                HomePageActivity.this.startActivity(myIntent);
                break;

            case R.id.foodpanel:

            case R.id.tablespanel:

        }
    }
}
