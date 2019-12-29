package com.example.reastaurantapp.Classes;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.reastaurantapp.HomePageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User {

    private static final String TAG = "TAG";
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String userType;
    private String phoneNumber;
    private String gender;
    private boolean isDeleted;

    public User(){

    }

    public User(String id, String firstName, String lastName, String email, String userType, String phoneNumber, String gender, boolean isDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isDeleted = isDeleted;
    }

    public void getUser_Firestore(final String id){
    }

    public void changeUserType(String userId, String newValue){

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isNotEmpty(){
        return this.id != null && this.firstName != null && this.lastName != null &&
                this.email != null && this.gender != null && this.userType != null &&
                this.phoneNumber != null;
    }
}
