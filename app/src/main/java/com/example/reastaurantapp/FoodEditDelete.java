package com.example.reastaurantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reastaurantapp.Classes.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FoodEditDelete extends AppCompatActivity {

    Uri ImageURI;

    EditText FoodName, FoodPrice, FoodDesc;
    Button pickImageBtn, EditFoodBtn, DeleteFoodBtn;
    ImageView FoodImage;

    Context mParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit_delete);

        mParent = this;

        FoodName = findViewById(R.id.ItemNameEdt);
        FoodPrice = findViewById(R.id.ItemPriceEdt);
        FoodDesc = findViewById(R.id.ItemDescEdt);

        pickImageBtn = findViewById(R.id.pickImageBtn);
        EditFoodBtn = findViewById(R.id.EditFoodBtn);
        DeleteFoodBtn = findViewById(R.id.DeleteFoodBtn);

        FoodImage = findViewById(R.id.FoodImage);

        InitializeViews();
    }

    public void InitializeViews(){
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("food").document(getIntent().getStringExtra("FoodID"));

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food item = documentSnapshot.toObject(Food.class);

                FoodName.setText(item.getItemName());
                FoodDesc.setText(item.getItemDesc());
                FoodPrice.setText(String.valueOf(item.getItemPrice()));

                Glide.with(mParent).load(Uri.parse(item.getItemPhoto())).into(FoodImage);
            }
        });
    }

    public void UpdateData(View view) {
    }

    public void PickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void DeleteFood(View view) {
        Food.deleteFood(getIntent().getStringExtra("FoodID"));
        finish();
        startActivity(new Intent(this, FoodPanel.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImageURI = data.getData();
            FoodImage.setImageURI(ImageURI);
        }
    }
}
