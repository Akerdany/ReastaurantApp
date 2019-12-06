package com.example.reastaurantapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class HomePageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    int Day, Month, Year, Hour, Miniute;
    int dayFinal, monthFinal, yearFinal, hourFinal, miniuteFinal;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_client);

        GR gr = new GR();
    //        FrameLayout fragment = findViewById(R.id.homepage_fragement);
        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement, gr).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ShowPopup(View v)
    {
        myDialog = new Dialog(this);
        myDialog.create();
        TextView txtclose;
        ImageView image = findViewById(R.id.viewImage) ;
        Button btnFollow;
        myDialog.setContentView(R.layout.activity_popupmenu);
        switch (v.getId())
        {
            case R.id.tablefour1:
                image.setImageResource(R.drawable.table4);
                break;
            case R.id.tabletwo1:
                image.setImageResource(R.drawable.table2);
                break;
        }
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
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
        DatePickerDialog datediag = new DatePickerDialog(HomePageActivity.this, HomePageActivity.this, Year, Month, Day);
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
        Miniute = c.get(Calendar.MINUTE);
        TimePickerDialog timediag = new TimePickerDialog(HomePageActivity.this, HomePageActivity.this, Hour, Miniute, DateFormat.is24HourFormat(this));
        timediag.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        hourFinal = hourOfDay;
        miniuteFinal = minute;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}
