package com.example.reastaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reastaurantapp.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    private EditText fNameData;
    private EditText lNameData;
    private EditText phoneNumberData;
    private EditText newPassword;
    private EditText confirmNewPassword;

    private TextView emailData;
    private TextView genderData;

    private Button update_btn;

    private Button confirmDeleteBtn;
    private Button cancelDeleteBtn;
    private Button delete_btn;

    private Button changePassword_btn;
    private Button cancelPasswordBtn;
    private Button confirmNewPasswordBtn;

    private Button backBtn;
    private Button signOut_btn;

    private ScrollView main_layout;
    private LinearLayout changePassword_layout;
    private LinearLayout delete_layout;
    private LinearLayout error_layout;

    private ProgressBar progressbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private User userObj = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        initializeComponents();
        initializeListeners();
        intializeUserData();
    }

    public void initializeComponents() {
        fNameData = findViewById(R.id.fNameData_myAccount);
        lNameData = findViewById(R.id.lNameData_myAccount);
        phoneNumberData = findViewById(R.id.phoneNumberData_myAccount);
        newPassword = findViewById(R.id.newPassword_myAccount);
        confirmNewPassword = findViewById(R.id.confirmNewPassword_myAccount);

        emailData = findViewById(R.id.emailData_myAccount);
        genderData = findViewById(R.id.genderData_myAccount);

        update_btn = findViewById(R.id.update_account);

        confirmDeleteBtn = findViewById(R.id.confirmDeleteBtn_myAccount);
        cancelDeleteBtn = findViewById(R.id.cancelDeleteBtn_myAccount);
        delete_btn = findViewById(R.id.delete_account);

        changePassword_btn = findViewById(R.id.changePassword_account);
        cancelPasswordBtn = findViewById(R.id.cancelPasswordBtn_myAccount);
        confirmNewPasswordBtn = findViewById(R.id.confirmNewPasswordBtn_myAccount);

        backBtn = findViewById(R.id.backBtn_myAccount);
        signOut_btn = findViewById(R.id.signOut_account);

        main_layout = findViewById(R.id.main_layout_myAccount);
        changePassword_layout = findViewById(R.id.changePassword_layout_myAccount);
        delete_layout = findViewById(R.id.delete_layout_myAccount);
        error_layout = findViewById(R.id.error_layout_myAccount);

        progressbar = findViewById(R.id.progressbar_myAccount);
    }

    public void initializeListeners() {

        signOut_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_layout.setVisibility(View.INVISIBLE);
                delete_layout.setVisibility(View.VISIBLE);

                confirmDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userObj.deleteUser(firebaseAuth.getCurrentUser().getUid());
                    }
                });

                cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_layout.setVisibility(View.INVISIBLE);
                        main_layout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        changePassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_layout.setVisibility(View.INVISIBLE);
                changePassword_layout.setVisibility(View.VISIBLE);

                confirmNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {

                            firebaseAuth.getCurrentUser()
                                    .updatePassword(newPassword.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AccountActivity.this, getText(R.string.changePassword_success), Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(AccountActivity.this, getText(R.string.changePassword_fail), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(AccountActivity.this, getText(R.string.confirmPassword_error_signUp), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                cancelPasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changePassword_layout.setVisibility(View.INVISIBLE);
                        main_layout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressbar();

                if (fNameData.getText().length() > 3) {

                    if (lNameData.getText().length() > 3) {

                        if (phoneNumberData.getText().length() == 11) {

                            userUpdate(firebaseAuth.getCurrentUser().getUid(), fNameData.getText().toString(),
                                    lNameData.getText().toString(), phoneNumberData.getText().toString());

                        } else {
                            hideProgressBar();
                            Toast.makeText(AccountActivity.this, getText(R.string.phoneNumber_error_signUp), Toast.LENGTH_LONG).show();
                            Toast.makeText(AccountActivity.this, "Phone number length: " + phoneNumberData.getText().length(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        hideProgressBar();
                        Toast.makeText(AccountActivity.this, getText(R.string.lastName_error_signUp), Toast.LENGTH_LONG).show();
                        Toast.makeText(AccountActivity.this, "Last Name length: " + fNameData.getText().length(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    hideProgressBar();
                    Toast.makeText(AccountActivity.this, getText(R.string.firstName_error_signUp), Toast.LENGTH_LONG).show();
                    Toast.makeText(AccountActivity.this, "First Name length: " + fNameData.getText().length(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void intializeUserData() {

        if (firebaseAuth.getCurrentUser() == null) {
            errorFunc();
        } else {

            DocumentReference docRef = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "In user class => DocumentSnapshot data: " + documentSnapshot.getData());

                        fNameData.setText(documentSnapshot.getString("firstName"));
                        lNameData.setText(documentSnapshot.getString("lastName"));
                        emailData.setText(documentSnapshot.getString("email"));
                        phoneNumberData.setText(documentSnapshot.getString("phoneNumber"));

                        if (documentSnapshot.getString("gender").equals("1")) {
                            genderData.setText(getText(R.string.maleGender_signUp));
                        } else {
                            genderData.setText(getText(R.string.femaleGender_signUp));
                        }

                    } else {
                        Log.d(TAG, "In AccountActivity Class => No such document");

                        errorFunc();
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "In AccountActivity Class => Get failed with ", e);

                            errorFunc();
                        }
                    });
        }

    }

    public void errorFunc() {
        main_layout.setVisibility(View.INVISIBLE);
        error_layout.setVisibility(View.VISIBLE);
    }

    public void userUpdate(final String id, String firstName, final String lastName, final String phoneNumber) {

        DocumentReference docRef = firebaseFirestore.collection("users").document(id);

        docRef.update("firstName", firstName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "In AccountActivity => DocumentSnapshot First Name successfully updated!");
                        userUpdate_lName(id, lastName, phoneNumber);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Log.w(TAG, "In AccountActivity => Error updating First Name of the document, with exception: ", e);
                        Toast.makeText(AccountActivity.this, getText(R.string.updateFail_myAccount), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void userUpdate_lName(final String id, final String lastName, final String phoneNumber) {
        DocumentReference docRef = firebaseFirestore.collection("users").document(id);

        docRef.update("lastName", lastName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "In AccountActivity => DocumentSnapshot Last Name successfully updated!");
                        userUpdate_phoneNumber(id, phoneNumber);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Log.w(TAG, "In AccountActivity => Error updating Last Name of the document, with exception: ", e);
                        Toast.makeText(AccountActivity.this, getText(R.string.updateFail_myAccount), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void userUpdate_phoneNumber(final String id, final String phoneNumber) {
        DocumentReference docRef = firebaseFirestore.collection("users").document(id);

        docRef.update("phoneNumber", phoneNumber)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressBar();
                        Log.d(TAG, "In AccountActivity => DocumentSnapshot Phone Number successfully updated!");
                        Toast.makeText(AccountActivity.this, getText(R.string.updateSuccess_myAccount), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Log.w(TAG, "In AccountActivity => Error updating Phone Number of the document, with exception: ", e);
                        Toast.makeText(AccountActivity.this, getText(R.string.updateFail_myAccount), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void showProgressbar(){
        main_layout.setAlpha((float) 0.2);
        progressbar.setVisibility(View.VISIBLE);

    }

    public void hideProgressBar(){
        main_layout.setAlpha((float) 1.0);
        progressbar.setVisibility(View.INVISIBLE);
    }

}
