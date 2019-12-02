package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_client);

        GR gr = new GR();

//        FrameLayout fragment = findViewById(R.id.homepage_fragement);

        getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragement, gr).commit();
    }
}
