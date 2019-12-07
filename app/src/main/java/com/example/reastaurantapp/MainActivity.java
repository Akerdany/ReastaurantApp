package com.example.reastaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email_editText;
    private EditText password_editText;
    private TextView signUp_link;
    private ProgressBar progressbar;
    private ScrollView main_layout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();

            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(intent);
        }

        initializeComponents();
    }


    public void signIn(View view) {

        if (!validate()) {
            return;
        }

        final String email = email_editText.getText().toString().trim();
        final String password = password_editText.getText().toString().trim();

        showProgressBar();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();

                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            disappearProgressBar();
                            Toast.makeText(MainActivity.this, getText(R.string.signInError_main), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void initializeComponents() {
        email_editText = findViewById(R.id.email_signIn);
        password_editText = findViewById(R.id.password_signIn);
        signUp_link = findViewById(R.id.signUpLink_signIn);

        main_layout = findViewById(R.id.main_layout);
        progressbar = findViewById(R.id.progressbar);

        signUp_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
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

    public void showProgressBar() {
        main_layout.setAlpha((float) 0.2);
        progressbar.setVisibility(View.VISIBLE);
    }

    public void disappearProgressBar() {
        main_layout.setAlpha((float) 1.0);
        progressbar.setVisibility(View.INVISIBLE);
    }
}
