package com.example.reastaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reastaurantapp.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName_editText;
    private EditText lastName_editText;
    private RadioGroup gender_radioGroup;
    private EditText phoneNumber_editText;
    private EditText email_editText;
    private EditText password_editText;
    private EditText confirmPassword_editText;
    private TextView signIn_link;

    private FirebaseFirestore databaseConnection;
    private FirebaseAuth firebaseAuth;

    private ScrollView main_layout;
    private ProgressBar progressbar;

    private boolean userCreationFlag = false;
    private boolean userIdFlag = false;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseConnection = FirebaseFirestore.getInstance();

        initializeComponents();
    }

    private void initializeComponents() {
        firstName_editText = findViewById(R.id.firstName_signUp);
        lastName_editText = findViewById(R.id.lastName_signUp);
        gender_radioGroup = findViewById(R.id.gender_signUp);
        phoneNumber_editText = findViewById(R.id.phoneNumber_signUp);
        email_editText = findViewById(R.id.email_signUp);
        password_editText = findViewById(R.id.password_signUp);
        confirmPassword_editText = findViewById(R.id.confirmPassword_signUp);
        signIn_link = findViewById(R.id.signInLink_signUp);

        main_layout = findViewById(R.id.mainLayout_signUp);
        progressbar = findViewById(R.id.progressbar_signUp);

        signIn_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signUp(View view) {

        if (!validation()) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email_editText.getText().toString(),
                password_editText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
                            userCreationFlag = true;
                        }
                    }
                });

        if (!userCreationFlag) {
            return;
        }

        showProgressBar();
        firebaseAuth.signInWithEmailAndPassword(email_editText.getText().toString(),
                password_editText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        } else {
                            disappearProgressBar();
                            userIdFlag = false;
                        }
                    }
                });

        if (!userIdFlag) {
            disappearProgressBar();
            Toast.makeText(SignUpActivity.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
            return;
        }

        int gender;
        int checkedRadioButtonId = gender_radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.male) {
            gender = 1;
        } else {
            gender = 2;
        }

        User user = new User(userID, firstName_editText.getText().toString(),
                lastName_editText.getText().toString(), email_editText.getText().toString(),
                2, phoneNumber_editText.getText().toString(), gender);

        databaseConnection.collection("users")
                .document(userID)
                .set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            disappearProgressBar();
                            finish();

                            Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        } else {
                            disappearProgressBar();
                            Toast.makeText(SignUpActivity.this, getText(R.string.singUp_fail), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void showProgressBar() {
        main_layout.setAlpha((float) 0.2);
        progressbar.setVisibility(View.VISIBLE);
    }

    public void disappearProgressBar() {
        main_layout.setAlpha((float) 1.0);
        progressbar.setVisibility(View.INVISIBLE);
    }

    public boolean validation() {

        int lastChildPos = gender_radioGroup.getChildCount() - 1;

        boolean firstNameFlag = false;
        boolean lastNameFlag = false;
        boolean genderFlag = false;
        boolean phoneNumberFlag = false;
        boolean emailErrorFlag = false;
        boolean passwordErrorFlag = false;
        boolean confirmPasswordFlag = false;

        if (firstName_editText.getText().toString().isEmpty()) {
            firstName_editText.setError(getText(R.string.firstName_errorEmpty_signUp));
            firstNameFlag = true;
        } else {
            if (!(firstName_editText.getText().toString().length() >= 3)) {
                firstName_editText.setError(getText(R.string.firstName_error_signUp));
                firstNameFlag = true;
            } else {
//                firstNameFlag = false;
                firstName_editText.setError(null);
            }
        }

        if (lastName_editText.getText().toString().isEmpty()) {
            lastName_editText.setError(getText(R.string.lastName_errorEmpty_signUp));
            lastNameFlag = true;
        } else {
            if (!(lastName_editText.getText().toString().length() >= 3)) {
                lastName_editText.setError(getText(R.string.lastName_error_signUp));
                lastNameFlag = true;
            } else {
//                lastNameFlag = false;
                lastName_editText.setError(null);
            }
        }

        if (gender_radioGroup.getCheckedRadioButtonId() == -1) {
            ((RadioButton) gender_radioGroup.getChildAt(lastChildPos)).setError(getText(R.string.gender_errorEmpty_signUp));
            genderFlag = true;
        } else {
//            genderFlag = false;
            ((RadioButton) gender_radioGroup.getChildAt(lastChildPos)).setError(null);
        }

        if (phoneNumber_editText.getText().toString().isEmpty()) {
            phoneNumber_editText.setError(getText(R.string.phoneNumber_errorEmpty_signUp));
            phoneNumberFlag = true;
        } else {
            if (!(phoneNumber_editText.getText().toString().length() == 11)) {
                phoneNumber_editText.setError(getText(R.string.phoneNumber_error_signUp));
                phoneNumberFlag = true;
            } else {
//                phoneNumberFlag = false;
                phoneNumber_editText.setError(null);
            }
        }

        if (email_editText.getText().toString().isEmpty()) {
            email_editText.setError(getText(R.string.email_errorEmpty_signUp));
            emailErrorFlag = true;
        } else {
            if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email_editText.getText().toString()).matches())) {
                email_editText.setError(getText(R.string.email_error_signUp));
                emailErrorFlag = true;
            } else {
//                emailErrorFlag = false;
                email_editText.setError(null);
            }
        }

        if (password_editText.getText().toString().isEmpty()) {
            password_editText.setError(getText(R.string.password_errorEmpty_signUp));
            passwordErrorFlag = true;
        } else {
            if (!(password_editText.getText().toString().length() == 8)) {
                password_editText.setError(getText(R.string.password_error_signUp));
                passwordErrorFlag = true;
            } else {
//                passwordErrorFlag = false;
                password_editText.setError(null);
            }
        }

        if (confirmPassword_editText.getText().toString().isEmpty()) {
            confirmPassword_editText.setError(getText(R.string.confirmPassword_errorEmpty_signUp));
            confirmPasswordFlag = true;
        } else {
            if (!confirmPassword_editText.getText().toString().equals(password_editText.getText().toString())) {
                confirmPassword_editText.setError(getText(R.string.confirmPassword_error_signUp));
                confirmPasswordFlag = true;
            } else {
//                confirmPasswordFlag = false;
                confirmPassword_editText.setError(null);
            }
        }

        return (firstNameFlag || lastNameFlag || genderFlag || phoneNumberFlag ||
                emailErrorFlag || passwordErrorFlag || confirmPasswordFlag);
    }

}
