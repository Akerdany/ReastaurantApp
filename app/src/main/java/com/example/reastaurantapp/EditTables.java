package com.example.reastaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reastaurantapp.Classes.Tables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EditTables extends AppCompatActivity
{
    TextView tableid, tablename;
    EditText smokingtype, windowtype, tablesearch;
    String Search, smkoingtype, window;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tables);
    }

    public void getidsearch(View view)
    {
        Search = new String();
        tablesearch = findViewById(R.id.searchtables);
        Search = tablesearch.getText().toString();
        if(Search.equals(""))
        {
            Toast.makeText(EditTables.this, "Please Enter a Vaild ID", Toast.LENGTH_LONG).show();
        }
        else
        {
            System.out.println(Search);
            getDetailstoedit(Search);
        }
    }

    public void getidedit(View view)
    {
        Search = new String();
        tablesearch = findViewById(R.id.searchtables);
        Search = tablesearch.getText().toString();
        updateTable(Search);
    }

    public void getDetailstoedit(final String ID)
    {
        tableid = findViewById(R.id.tableidretrieved);
        tablename = findViewById(R.id.tablenameretrieved);
        smokingtype = findViewById(R.id.smokingtyperetrieved);
        windowtype = findViewById(R.id.windowtyperetrieved);

        FirebaseFirestore firebaseFirestore;
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firebaseFirestore.collection("tables").document(ID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if (documentSnapshot.exists())
                {
                    tableid.setText(ID);
                    tablename.setText(documentSnapshot.getString("tablename"));
                    smokingtype.setText(documentSnapshot.getString("smokingtype"));
                    windowtype.setText(documentSnapshot.getString("windowtype"));
                }
                else
                {
                    Toast.makeText(EditTables.this, "Table Not Found!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateTable(String ID)
    {
        smokingtype = findViewById(R.id.smokingtyperetrieved);
        windowtype = findViewById(R.id.windowtyperetrieved);

        smkoingtype = smokingtype.getText().toString();
        window = windowtype.getText().toString();

        if(smkoingtype.equals("") && window.equals(""))
        {
            Toast.makeText(EditTables.this, "Please Enter a valid Type", Toast.LENGTH_LONG).show();
        }
        if(window.equals("") || smkoingtype.equals(""))
        {
            Toast.makeText(EditTables.this, "Please Enter a valid Type", Toast.LENGTH_LONG).show();
        }
        else
        {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            DocumentReference docRef = firebaseFirestore.collection("tables").document(ID);

            docRef.update("smokingtype", smkoingtype).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {

                }
            })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                        }
                    });

            docRef = firebaseFirestore.collection("tables").document(ID);

            docRef.update("windowtype", window).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                @Override
                public void onSuccess(Void aVoid)
                {
                    Toast.makeText(EditTables.this, "Table edited successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(EditTables.this, "An error occurred!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

}
