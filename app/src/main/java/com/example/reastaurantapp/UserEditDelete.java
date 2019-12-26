package com.example.reastaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.reastaurantapp.Classes.User;

public class UserEditDelete extends AppCompatActivity {

    public static final String TAG = "TAG";
    private String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_delete);

        Intent previous_intent = getIntent();
        if (previous_intent.getStringExtra("UserID") != null) {

            userID = previous_intent.getStringExtra("UserID");

            Log.w(TAG, "The userType: " + userID);

            User tempUserEdit_Delete = new User();
            tempUserEdit_Delete.getUser_Firestore(userID);

//            Toast.makeText(UserEditDelete.this, "UserType: " + userID,
//                    Toast.LENGTH_SHORT).show();
        }else{
            Log.w(TAG, "The userType: " + previous_intent.getStringExtra("UserID"));
        }
    }


}
