package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText email_editText;
    private EditText password_editText;
    private Button signIn_btn;
    private TextView signUp_link;

    private Toast errorMessage;
    private String emailErrorMessage = "Email is empty, please fill it.";
    private String passwordErrorMessage = "Password is empty, please fill it";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeListeners();
    }

    private void initializeComponents(){
        email_editText = findViewById(R.id.email_signIn);
        password_editText = findViewById(R.id.password_signIn);
        signIn_btn = findViewById(R.id.btn_signIn);
        signUp_link = findViewById(R.id.signUpLink_signIn);

    }

    private void initializeListeners(){

        signUp_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(View view) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        if(email_editText.getText().toString().equals("")){

            errorMessage.makeText(this, emailErrorMessage, Toast.LENGTH_LONG).show();
            return;
        }

        if(password_editText.getText().toString().equals("")){

            errorMessage.makeText(this, passwordErrorMessage, Toast.LENGTH_LONG).show();
            return;
        }
    }
}
