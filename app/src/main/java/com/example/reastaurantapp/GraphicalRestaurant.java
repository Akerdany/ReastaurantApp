package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GraphicalRestaurant extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    String tablename;
    int Day, Month, Year, Hour, Minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    Dialog myDialog;
    TextView close;
    ImageView image;
    Map<String, Object> Reservation;

    FirebaseFirestore databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical_restaurant);
        image = findViewById(R.id.viewImage);
        myDialog = new Dialog(this);
        Reservation = new HashMap<>();
        databaseConnection = FirebaseFirestore.getInstance();
    }


    public void ShowPopup(View view)
    {
        tablename = view.getTag().toString();
        myDialog.setContentView(R.layout.activity_popupmenu);
//        switch (view.getId())
//        {
//            case R.id.tablefour1:
//                image.setImageResource(R.drawable.table4);
//                break;
//            case R.id.tabletwo1:
//                image.setImageResource(R.drawable.table2);
//                break;
//        }
        close = myDialog.findViewById(R.id.txtclose);
        close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
        DatePickerDialog datediag = new DatePickerDialog(GraphicalRestaurant.this, GraphicalRestaurant.this, Year, Month, Day);
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
        TimePickerDialog timediag = new TimePickerDialog(GraphicalRestaurant.this, GraphicalRestaurant.this, Hour, Minute, DateFormat.is24HourFormat(this));
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

        databaseConnection.collection("reservation").document().set(Reservation).addOnCompleteListener(this, new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(GraphicalRestaurant.this, GraphicalRestaurant.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(GraphicalRestaurant.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }
}
