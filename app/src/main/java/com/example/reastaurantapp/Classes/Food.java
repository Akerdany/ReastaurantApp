package com.example.reastaurantapp.Classes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Food {

    private String ItemID;
    private String ItemName;
    private String ItemDesc;
    private String ItemPhoto;

    private int ItemPrice;

    public Food(){

    }

    public Food(String itemID, String itemName, String itemDesc, int itemPrice, String itemPhoto) {
        ItemID = itemID;
        ItemName = itemName;
        ItemDesc = itemDesc;
        ItemPrice = itemPrice;
        ItemPhoto = itemPhoto;
    }

    public Food(String itemID, String itemName, String itemDesc, int itemPrice) {
        ItemID = itemID;
        ItemName = itemName;
        ItemDesc = itemDesc;
        ItemPrice = itemPrice;
    }

    public static void deleteFood(String id){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("food").document(id);
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("FoodImages").child(id);

        docRef.delete();
        imageRef.delete();

//        docRef.update("isDeleted", true)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemPhoto() {
        return ItemPhoto;
    }

    public void setItemPhoto(String itemPhoto) {
        ItemPhoto = itemPhoto;
    }
}
