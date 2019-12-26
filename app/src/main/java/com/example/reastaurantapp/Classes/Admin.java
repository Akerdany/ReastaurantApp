package com.example.reastaurantapp.Classes;

public class Admin extends User  {
    public Admin(String id, String firstName, String lastName, String email, String userType, String phoneNumber, String gender, boolean isDeleted) {
        super(id, firstName, lastName, email, userType, phoneNumber, gender, isDeleted);
    }
}
