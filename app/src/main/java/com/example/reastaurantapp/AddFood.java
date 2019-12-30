package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reastaurantapp.Classes.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddFood extends AppCompatActivity {

    public Uri ImageURI;

    StorageTask storageUploadTask;
    StorageReference storageRef;
    FirebaseFirestore databaseConnection;

    EditText FoodName, FoodPrice, FoodDesc;
    Button pickImageBtn, AddFoodBtn;
    ImageView FoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        storageRef = FirebaseStorage.getInstance().getReference("FoodImages");
        databaseConnection = FirebaseFirestore.getInstance();

        FoodName = findViewById(R.id.ItemNameEdt);
        FoodPrice = findViewById(R.id.ItemPriceEdt);
        FoodDesc = findViewById(R.id.ItemDescEdt);

        pickImageBtn = findViewById(R.id.pickImageBtn);
        AddFoodBtn = findViewById(R.id.AddFoodBtn);

        FoodImage = findViewById(R.id.FoodImage);
    }

    public void PickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void UploadFoodData(View view) {
        if (storageUploadTask != null && storageUploadTask.isInProgress()){
            Toast.makeText(this, "Adding Data in progress", Toast.LENGTH_SHORT).show();

        }else {
            final DocumentReference documentReference = databaseConnection.collection("food").document();

            final String documentID = documentReference.getId();

            final StorageReference imageRef = storageRef.child(documentID);

            storageUploadTask = imageRef.putFile(ImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(AddFood.this, "Upload Image Done", Toast.LENGTH_SHORT).show();

                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    Toast.makeText(AddFood.this, uri.toString() + "", Toast.LENGTH_SHORT).show();
                                    Food item = new Food(documentID,
                                            FoodName.getText().toString(),
                                            FoodDesc.getText().toString(),
                                            Integer.parseInt(FoodPrice.getText().toString()),
                                            uri.toString());
                                    documentReference.set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AddFood.this, "Food Data Added", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
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
