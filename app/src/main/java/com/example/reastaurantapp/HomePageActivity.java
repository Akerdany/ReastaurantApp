package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class HomePageActivity extends AppCompatActivity
{
    BottomNavigationView bottomNav;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
    new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment selectedFragment = null;
            //TODO: Add the other menu cases buttons
            switch (item.getItemId())
            {
                case R.id.nav_home_icon:
                    selectedFragment = new GR();
                    break;
                case R.id.nav_more_icon:
                    selectedFragment = new MorePage();
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

        bottomNav = findViewById(R.id.homepage_bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navigationListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement,
                new GR()).commit();
    }

    public void SwitchToGR(View view)
    {
        Intent intent = new Intent(this, GraphicalRestaurant.class);
        startActivity(intent);
    }
}
