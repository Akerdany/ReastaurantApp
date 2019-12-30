package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reastaurantapp.Classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserEditDelete extends AppCompatActivity {

    //            Toast.makeText(UserEditDelete.this, "UserType: " + userID,
//                    Toast.LENGTH_SHORT).show();

    public static final String TAG = "TAG";
    private String userID = null;

    private TextView fullName_edit_delete;
    private TextView email_edit_delete;
    private TextView phoneNumber_edit_delete;
    private TextView gender_edit_delete;
    private Spinner userType_edit_delete_spinner;
    private TextView status_edit_delete;

    private LinearLayout errorMessageLayout;
    private LinearLayout deleteConfirmationMessage;
    private LinearLayout confirmReActivate_user_edit_delete_frame_layout;
    private LinearLayout confirmUpdate_user_edit_delete_frame_layout;
    private ConstraintLayout mainLayout;

    private Button update_btn_edit_delete;
    private Button getBack_btn_user_edit_delete;
    private Button delete_btn_edit_delete;
    private Button reactivate_btn_edit_delete;
    private Button confirmDeleteBtn_user_edit_delete;
    private Button getBackConfirmDeleteBtn_user_edit_delete;
    private Button getBackReActivateBtn_user_edit_delete;
    private Button confirmReActivateBtn_user_edit_delete;
    private Button getBackUpdateBtn_user_edit_delete;
    private Button confirmUpdateBtn_user_edit_delete;

    FirebaseFirestore firebaseFirestore;
    User tempUserEdit_Delete = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_delete);

        errorMessageLayout = findViewById(R.id.errorMessage_user_edit_delete_frame_layout);
        mainLayout = findViewById(R.id.mainLayout_user_edit_delete);
        getBack_btn_user_edit_delete = findViewById(R.id.getBackBtn_user_edit_delete);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent previous_intent = getIntent();
        if (previous_intent.getStringExtra("UserID") != null) {

            userID = previous_intent.getStringExtra("UserID");

            Log.w(TAG, "The userType: " + userID);

            DocumentReference docRef = firebaseFirestore.collection("users").document(userID);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Log.d(TAG, "In user class => DocumentSnapshot data: " + documentSnapshot.getData());

                        tempUserEdit_Delete.setId(documentSnapshot.getString("id"));
                        tempUserEdit_Delete.setFirstName(documentSnapshot.getString("firstName"));
                        tempUserEdit_Delete.setLastName(documentSnapshot.getString("lastName"));
                        tempUserEdit_Delete.setEmail(documentSnapshot.getString("email"));
                        tempUserEdit_Delete.setUserType(documentSnapshot.getString("userType"));
                        tempUserEdit_Delete.setPhoneNumber(documentSnapshot.getString("phoneNumber"));
                        tempUserEdit_Delete.setGender(documentSnapshot.getString("gender"));
                        tempUserEdit_Delete.setIsDeleted(documentSnapshot.getBoolean("isDeleted"));

                        if (tempUserEdit_Delete.isNotEmpty()) {
                            initialize(tempUserEdit_Delete);
                        } else {
                            Log.d(TAG, "In UserEditDelete Class => No such document");

                            mainLayout.setVisibility(View.INVISIBLE);
                            errorMessageLayout.setVisibility(View.VISIBLE);
                            getBack_btn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                        }

                    } else {
                        Log.d(TAG, "In UserEditDelete Class => No such document");

                        mainLayout.setVisibility(View.INVISIBLE);
                        errorMessageLayout.setVisibility(View.VISIBLE);
                        getBack_btn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "In UserEditDelete Class => Get failed with ", e);

                            mainLayout.setVisibility(View.INVISIBLE);
                            errorMessageLayout.setVisibility(View.VISIBLE);
                            getBack_btn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                        }
                    });
        } else {
            Log.w(TAG, "The userType: " + previous_intent.getStringExtra("UserID"));

            mainLayout.setVisibility(View.INVISIBLE);
            errorMessageLayout.setVisibility(View.VISIBLE);
            getBack_btn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void initialize(final User tempUserEdit_Delete) {

        fullName_edit_delete = findViewById(R.id.nameretrieved);
        email_edit_delete = findViewById(R.id.emailretrieved);
        phoneNumber_edit_delete = findViewById(R.id.phoneretrieved);
        gender_edit_delete = findViewById(R.id.genderretrieved);
        userType_edit_delete_spinner = findViewById(R.id.usertyperetrieved);
        status_edit_delete = findViewById(R.id.statusretrieved);
        update_btn_edit_delete = findViewById(R.id.update_user_edit_delete);
        delete_btn_edit_delete = findViewById(R.id.delete_user_edit_delete);
        reactivate_btn_edit_delete = findViewById(R.id.reactivate_user_edit_delete);

        deleteConfirmationMessage = findViewById(R.id.confirmDelete_user_edit_delete_frame_layout);
        confirmDeleteBtn_user_edit_delete = findViewById(R.id.confirmDeleteBtn_user_edit_delete);
        getBackConfirmDeleteBtn_user_edit_delete = findViewById(R.id.getBackConfirmDeleteBtn_user_edit_delete);

        confirmReActivate_user_edit_delete_frame_layout = findViewById(R.id.confirmReActivate_user_edit_delete_frame_layout);
        getBackReActivateBtn_user_edit_delete = findViewById(R.id.getBackReActivateBtn_user_edit_delete);
        confirmReActivateBtn_user_edit_delete = findViewById(R.id.confirmReActivateBtn_user_edit_delete);

        confirmUpdate_user_edit_delete_frame_layout = findViewById(R.id.confirmUpdate_user_edit_delete_frame_layout);
        getBackUpdateBtn_user_edit_delete = findViewById(R.id.getBackUpdateBtn_user_edit_delete);
        confirmUpdateBtn_user_edit_delete = findViewById(R.id.confirmUpdateBtn_user_edit_delete);

        String fullName = tempUserEdit_Delete.getFirstName() + " " + tempUserEdit_Delete.getLastName();
        fullName_edit_delete.setText(fullName);
        email_edit_delete.setText(tempUserEdit_Delete.getEmail());
        phoneNumber_edit_delete.setText(tempUserEdit_Delete.getPhoneNumber());

        if (tempUserEdit_Delete.getGender().equals("1")) {
            gender_edit_delete.setText(getText(R.string.maleGender_signUp));
        } else {
            gender_edit_delete.setText(getText(R.string.femaleGender_signUp));
        }

        if (!tempUserEdit_Delete.getIsDeleted()) {
            status_edit_delete.setText(getText(R.string.status_account_active));
            delete_btn_edit_delete.setVisibility(View.VISIBLE);
            reactivate_btn_edit_delete.setVisibility(View.INVISIBLE);

            delete_btn_edit_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainLayout.setVisibility(View.INVISIBLE);
                    deleteConfirmationMessage.setVisibility(View.VISIBLE);

                    getBackConfirmDeleteBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteConfirmationMessage.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    confirmDeleteBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tempUserEdit_Delete.deleteUser(tempUserEdit_Delete.getId());
                            finish();
                            startActivity(getIntent());
                        }
                    });
                }
            });
        } else {
            status_edit_delete.setText(getText(R.string.status_account_deleted));
            delete_btn_edit_delete.setVisibility(View.INVISIBLE);
            reactivate_btn_edit_delete.setVisibility(View.VISIBLE);

            reactivate_btn_edit_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainLayout.setVisibility(View.INVISIBLE);
                    confirmReActivate_user_edit_delete_frame_layout.setVisibility(View.VISIBLE);

                    getBackReActivateBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmReActivate_user_edit_delete_frame_layout.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    confirmReActivateBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tempUserEdit_Delete.reactivateUser(tempUserEdit_Delete.getId());
                            finish();
                            startActivity(getIntent());
                        }
                    });

                }
            });
        }

        ArrayAdapter<String> userType_adapter = new ArrayAdapter<String>(UserEditDelete.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.userTypes_array));
        userType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType_edit_delete_spinner.setAdapter(userType_adapter);

        String tempUserType_spinner = null;
        if (tempUserEdit_Delete.getUserType().equals("1")) {
            tempUserType_spinner = "Admin";
        } else if (tempUserEdit_Delete.getUserType().equals("3")) {
            tempUserType_spinner = "Chef";
        } else {
            tempUserType_spinner = "Client";
        }

        int spinnerPosition = userType_adapter.getPosition(tempUserType_spinner);
        userType_edit_delete_spinner.setSelection(spinnerPosition);

        userType_edit_delete_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                update_btn_edit_delete.setVisibility(View.VISIBLE);
                final String newUserType_selected = userType_edit_delete_spinner.getSelectedItem().toString();

                update_btn_edit_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainLayout.setVisibility(View.INVISIBLE);
                        confirmUpdate_user_edit_delete_frame_layout.setVisibility(View.VISIBLE);

                        getBackUpdateBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmUpdate_user_edit_delete_frame_layout.setVisibility(View.INVISIBLE);
                                mainLayout.setVisibility(View.VISIBLE);
                            }
                        });

                        confirmUpdateBtn_user_edit_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (newUserType_selected){
                                    case "Admin":
                                        tempUserEdit_Delete.changeUserType(tempUserEdit_Delete.getId(), "1");
                                        finish();
                                        startActivity(getIntent());
                                        break;

                                    case "Client":
                                        tempUserEdit_Delete.changeUserType(tempUserEdit_Delete.getId(), "2");
                                        finish();
                                        startActivity(getIntent());
                                        break;

                                    case "Chef":
                                        tempUserEdit_Delete.changeUserType(tempUserEdit_Delete.getId(), "3");
                                        finish();
                                        startActivity(getIntent());
                                        break;
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

}
