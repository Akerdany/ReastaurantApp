package com.example.reastaurantapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity{

    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Button btnFollow;
        myDialog.setContentView(R.layout.activity_popupmenu);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
