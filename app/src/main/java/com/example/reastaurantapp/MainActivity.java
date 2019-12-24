package com.example.reastaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = null;
    public int tempUserType = 0;
    private EditText email_editText;
    private EditText password_editText;
    private TextView signUp_link;
    private ProgressBar progressbar;
    private ScrollView main_layout;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
            getUserType(firebaseAuth.getCurrentUser().getUid());
            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
            intent.putExtra("userType", tempUserType);

            finish();
            startActivity(intent);
        }else if(!firebaseAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(MainActivity.this, "Verify your email first",
                    Toast.LENGTH_SHORT).show();
        }

        initializeComponents();
    }


    public void signIn(View view) {

        if (!validate()) {
            return;
        }

        final String email = email_editText.getText().toString().trim();
        final String password = password_editText.getText().toString().trim();

        showProgressBar();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.w(TAG, "task " + task.getResult().toString());
                        if (task.isSuccessful()) {
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                Log.w(TAG, "User verified his email");
                                getUserType(firebaseAuth.getCurrentUser().getUid());
                            }else{
                                hideProgressBar();
                                Log.w(TAG, "User did not verify his email code: " + firebaseAuth.getCurrentUser().isEmailVerified());
                                Toast.makeText(MainActivity.this, "You should verify your email first",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            hideProgressBar();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Email or Password may be wrong.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            hideProgressBar();
                            Log.w(TAG, task.getException());
                            Toast.makeText(MainActivity.this, "Email or Password may be wrong.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeComponents() {
        email_editText = findViewById(R.id.email_signIn);
        password_editText = findViewById(R.id.password_signIn);
        signUp_link = findViewById(R.id.signUpLink_signIn);

        main_layout = findViewById(R.id.main_layout);
        progressbar = findViewById(R.id.progressbar);

        signUp_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean validate() {

        boolean emailErrorFlag = false;
        boolean passwordErrorFlag = false;

        if (email_editText.getText().toString().isEmpty()) {
            email_editText.setError(getText(R.string.emailError_main));
            emailErrorFlag = true;
        }

        if (password_editText.getText().toString().isEmpty()) {
            password_editText.setError(getText(R.string.passwordError_main));
            passwordErrorFlag = true;
        }

        if (emailErrorFlag || passwordErrorFlag) {
            return false;
        }

        email_editText.setError(null);
        password_editText.setError(null);

        return true;
    }

    public void showProgressBar() {
        main_layout.setAlpha((float) 0.2);
        progressbar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        main_layout.setAlpha((float) 1.0);
        progressbar.setVisibility(View.INVISIBLE);
    }

    public void getUserType(String userID) {
        DocumentReference docRef = firebaseFirestore.collection("users").document(userID);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    hideProgressBar();
                    Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                    Log.d(TAG, "db firstName getString() is: " + documentSnapshot.getString("userType"));
                    tempUserType = Integer.parseInt(documentSnapshot.getString("userType"));

                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    intent.putExtra("userType", tempUserType);

                    finish();
                    startActivity(intent);

                } else {
                    hideProgressBar();
                    Log.d(TAG, "No such document");
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();
                        Log.d(TAG, "get failed with ", e);
                    }
                });
    }
}
