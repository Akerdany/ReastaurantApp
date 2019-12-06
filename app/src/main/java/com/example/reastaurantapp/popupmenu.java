package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import android.text.format.DateFormat;
import java.util.Calendar;

public class popupmenu extends AppCompatActivity
{

    Button b_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupmenu);
        b_pick = (Button) findViewById(R.id.btnfollow);

    }


}
