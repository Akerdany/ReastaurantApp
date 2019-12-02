package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName_editText;
    private EditText lastName_editText;
    private RadioGroup gender_radioGroup;
    private EditText dateOfBirth_datePicker;
    private EditText phoneNumber_editText;
    private EditText email_editText;
    private EditText password_editText;
    private EditText confirmPassword_editText;
    private TextView signIn_link;
    private Button signUp_btn;

    private boolean flag = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeComponents();
        initializeListeners();
    }

    private void initializeComponents() {
        firstName_editText  = findViewById(R.id.firstName_signUp);
        lastName_editText  = findViewById(R.id.lastName_signUp);
        gender_radioGroup  = findViewById(R.id.gender_signUp);
        dateOfBirth_datePicker  = findViewById(R.id.dateOfBirth_signUp);
        phoneNumber_editText  = findViewById(R.id.phoneNumber_signUp);
        email_editText = findViewById(R.id.email_signUp);
        password_editText = findViewById(R.id.password_signUp);
        confirmPassword_editText = findViewById(R.id.confirmPassword_signUp);
        signIn_link = findViewById(R.id.signInLink_signUp);
        signUp_btn = findViewById(R.id.btn_signUp);
    }

    private void initializeListeners() {

        signIn_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signUp(View view) {

        if(!validation()){
            return;
        }

        Map<String, Object> user = new HashMap<>();

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        flag = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag = false;
                    }
                });

        if(flag){
            Toast.makeText(this, "Success",Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this, "Fail",Toast.LENGTH_LONG);
        }


        //TODO: Save data in database

    }

    //TODO: Check the sequence of the validation
    public boolean validation() {

        int lastChildPos = gender_radioGroup.getChildCount()-1;

        boolean firstNameFlag = false;
        boolean lastNameFlag = false;
        boolean genderFlag = false;
        boolean dateOfBirthFlag = false;
        boolean phoneNumberFlag = false;
        boolean emailErrorFlag = false;
        boolean passwordErrorFlag = false;
        boolean confirmPasswordFlag = false;

        if (firstName_editText.getText().toString().isEmpty()) {
            firstName_editText.setError(getText(R.string.firstName_errorEmpty_signUp));
            firstNameFlag = true;
        }else{
            firstName_editText.setError(null);
        }

        if (lastName_editText.getText().toString().isEmpty()) {
            lastName_editText.setError(getText(R.string.lastName_errorEmpty_signUp));
            lastNameFlag = true;
        }else{
            lastName_editText.setError(null);
        }

        if (gender_radioGroup.getCheckedRadioButtonId() == -1) {
            ((RadioButton)gender_radioGroup.getChildAt(lastChildPos)).setError(getText(R.string.gender_errorEmpty_signUp));
            genderFlag = true;
        }else{
            ((RadioButton)gender_radioGroup.getChildAt(lastChildPos)).setError(null);
        }

        if (dateOfBirth_datePicker.getText().toString().isEmpty()) {
            dateOfBirth_datePicker.setError(getText(R.string.dateOfBirth_errorEmpty_signUp));
            dateOfBirthFlag = true;
        }else{
            dateOfBirth_datePicker.setError(null);
        }

        if (phoneNumber_editText.getText().toString().isEmpty()) {
            phoneNumber_editText.setError(getText(R.string.phoneNumber_errorEmpty_signUp));
            phoneNumberFlag = true;
        }else{
            phoneNumber_editText.setError(null);
        }

        if (email_editText.getText().toString().isEmpty()) {
            email_editText.setError(getText(R.string.email_errorEmpty_signUp));
            emailErrorFlag = true;
        }else{
            email_editText.setError(null);
        }

        if (password_editText.getText().toString().isEmpty()) {
            password_editText.setError(getText(R.string.password_errorEmpty_signUp));
            passwordErrorFlag = true;
        }else{
            password_editText.setError(null);
        }

        if (confirmPassword_editText.getText().toString().isEmpty()) {
            confirmPassword_editText.setError(getText(R.string.confirmPassword_errorEmpty_signUp));
            confirmPasswordFlag = true;
        }else{
            confirmPassword_editText.setError(null);
        }

        if (firstNameFlag || lastNameFlag || genderFlag || dateOfBirthFlag || phoneNumberFlag ||
                emailErrorFlag || passwordErrorFlag || confirmPasswordFlag) {
            return false;
        }

        if(firstName_editText.getText().toString().length() == 3){
            firstName_editText.setError(getText(R.string.firstName_error_signUp));
            firstNameFlag = true;
        }else{
            firstName_editText.setError(null);
        }

        if(lastName_editText.getText().toString().length() == 3){
            lastName_editText.setError(getText(R.string.lastName_error_signUp));
            lastNameFlag = true;
        }else{
            lastName_editText.setError(null);
        }

        //TODO:Date of birth validation

        if(phoneNumber_editText.getText().toString().length() == 11){
            phoneNumber_editText.setError(getText(R.string.phoneNumber_error_signUp));
            phoneNumberFlag = true;
        }else{
            phoneNumber_editText.setError(null);
        }

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email_editText.getText().toString()).matches()){
            email_editText.setError(getText(R.string.email_error_signUp));
            emailErrorFlag = true;
        }else{
            email_editText.setError(null);
        }

        if(password_editText.getText().toString().length() == 8){
            password_editText.setError(getText(R.string.password_error_signUp));
            passwordErrorFlag = true;
        }else{
            password_editText.setError(null);
        }

        if(confirmPassword_editText.getText().toString().equals(password_editText.getText().toString())){
            confirmPassword_editText.setError(getText(R.string.confirmPassword_error_signUp));
            confirmPasswordFlag = true;
        }else{
            confirmPassword_editText.setError(null);
        }

        if (firstNameFlag || lastNameFlag || dateOfBirthFlag || phoneNumberFlag ||
                emailErrorFlag || passwordErrorFlag || confirmPasswordFlag) {
            return false;
        }

        return true;
    }

}
