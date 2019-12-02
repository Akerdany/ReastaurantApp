package com.example.reastaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Write a message to the database
    // FirebaseDatabase database = FirebaseDatabase.getInstance();
    // DatabaseReference myRef = database.getReference("message");

    private EditText email_editText;
    private EditText password_editText;
    private TextView signUp_link;
    private Button signIn_btn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeListeners();
    }

    private void initializeComponents() {
        email_editText = findViewById(R.id.email_signIn);
        password_editText = findViewById(R.id.password_signIn);
        signIn_btn = findViewById(R.id.btn_signIn);
        signUp_link = findViewById(R.id.signUpLink_signIn);
    }

    private void initializeListeners() {

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

        if (!validate()) {
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("email", email_editText.getText().toString());
        user.put("password", password_editText.getText().toString());

        // Add a new document with a generated ID
        db.collection("users")
                .add(user);

        //TODO: Database read to logIn()
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(intent);

    }

    public boolean validate() {

        boolean emailErrorFlag = false;
        boolean passwordErrorFlag = false;

        if (email_editText.getText().toString().isEmpty()) {
            email_editText.setError(getText(R.string.emailError_main));
            emailErrorFlag = true;
        }

        if (password_editText.getText().toString().isEmpty()) {
            password_editText.setError(getText(R.string.passwordError_main));
            passwordErrorFlag = true;
        }

        if (emailErrorFlag || passwordErrorFlag) {
            return false;
        }

        email_editText.setError(null);
        password_editText.setError(null);

        return true;
    }

}
