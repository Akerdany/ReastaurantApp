package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reastaurantapp.Classes.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FoodEditDelete extends AppCompatActivity {

    Uri ImageURI;

    StorageTask storageUploadTask;

    ConstraintLayout main_layout;
    EditText FoodName, FoodPrice, FoodDesc;
    Button pickImageBtn, EditFoodBtn, DeleteFoodBtn;
    ImageView FoodImage;
    ProgressBar uploadProgressBar;

    Context mParent;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit_delete);

        mParent = this;
        ID = getIntent().getStringExtra("FoodID");

        main_layout = findViewById(R.id.main_layout);

        FoodName = findViewById(R.id.ItemNameEdt);
        FoodPrice = findViewById(R.id.ItemPriceEdt);
        FoodDesc = findViewById(R.id.ItemDescEdt);

        pickImageBtn = findViewById(R.id.pickImageBtn);
        EditFoodBtn = findViewById(R.id.EditFoodBtn);
        DeleteFoodBtn = findViewById(R.id.DeleteFoodBtn);

        FoodImage = findViewById(R.id.FoodImage);

        uploadProgressBar = findViewById(R.id.uploadProgressBar);

        InitializeViews();
    }

    public void InitializeViews(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("food").document(ID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food item = documentSnapshot.toObject(Food.class);

                FoodName.setText(item.getItemName());
                FoodDesc.setText(item.getItemDesc());
                FoodPrice.setText(String.valueOf(item.getItemPrice()));

                Glide.with(FoodEditDelete.this).load(Uri.parse(item.getItemPhoto())).into(FoodImage);
            }
        });
    }

    public void UpdateData(View view) {
        if (storageUploadTask != null && storageUploadTask.isInProgress()){
            Toast.makeText(this, "Editing Data in progress", Toast.LENGTH_SHORT).show();
        }else {

            if (FoodName.getText().toString().isEmpty()){
                FoodName.setError(getText(R.string.FoodName_Missing));
            }else if (FoodPrice.getText().toString().isEmpty()){
                FoodPrice.setError(getText(R.string.FoodPrice_Missing));
            }else if (FoodDesc.getText().toString().isEmpty()){
                FoodDesc.setError(getText(R.string.FoodDesc_Missing));
            }else if (ImageURI == null){
                Toast.makeText(FoodEditDelete.this, "Please Pick New Image", Toast.LENGTH_SHORT).show();
            }else {
                final DocumentReference docRef = FirebaseFirestore.getInstance().collection("food").document(ID);
                final StorageReference imageRef = FirebaseStorage.getInstance().getReference("FoodImages").child(ID);

                showProgressbar();

                storageUploadTask = imageRef.putFile(ImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Map<String, Object> newValues = new HashMap<>();

                                newValues.put("itemName", FoodName.getText().toString());
                                newValues.put("itemDesc", FoodDesc.getText().toString());
                                newValues.put("itemPrice", Integer.parseInt(FoodPrice.getText().toString()));
                                newValues.put("itemPhoto", uri.toString());

                                docRef.update(newValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        hideProgressBar();
                                        Toast.makeText(FoodEditDelete.this, "Food Edited", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressBar();
                                        Toast.makeText(FoodEditDelete.this, "Error in Updating Data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                hideProgressBar();
                                Toast.makeText(FoodEditDelete.this, "Error in getting image link", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Toast.makeText(FoodEditDelete.this, "Error in updating Image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void PickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void DeleteFood(View view) {
        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("food").document(ID);
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("FoodImages").child(ID);

        showProgressbar();

        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressBar();
                        Toast.makeText(FoodEditDelete.this, "Food Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(FoodEditDelete.this, FoodPanel.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Toast.makeText(FoodEditDelete.this, "Couldn't Delete Food Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressBar();
                Toast.makeText(FoodEditDelete.this, "Couldn't Delete Food Data and image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageURI = data.getData();
            FoodImage.setImageURI(ImageURI);
        }
    }

    public void showProgressbar(){
        main_layout.setAlpha((float) 0.2);
        uploadProgressBar.setVisibility(View.VISIBLE);

    }

    public void hideProgressBar(){
        main_layout.setAlpha((float) 1.0);
        uploadProgressBar.setVisibility(View.INVISIBLE);
    }
}
