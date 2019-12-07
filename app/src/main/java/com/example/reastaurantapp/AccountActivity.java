package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private Button signOut_btn;
    private TextView accountData;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth = FirebaseAuth.getInstance();

        initialize();
    }

    private void initialize() {

        //TODO:Temporary account data
        accountData = findViewById(R.id.AccountData_Temp);
        accountData.setText("Id: " + firebaseAuth.getCurrentUser().getUid() + "\n" +
                                "Email: " + firebaseAuth.getCurrentUser().getEmail() + "\n");


        signOut_btn = findViewById(R.id.signOut_account);
        signOut_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                finish();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
