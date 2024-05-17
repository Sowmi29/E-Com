package com.example.e_com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GetUserDetails extends AppCompatActivity {
    String email;
    EditText nameEt, addressEt, numberEt;
    FirebaseFirestore db;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);

        // Get the email from the previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("EXTRA_KEY");

        nameEt = findViewById(R.id.nameEt);
        addressEt = findViewById(R.id.addressEt);
        numberEt = findViewById(R.id.numberEt);
        saveBtn = findViewById(R.id.saveBtn);

        db = FirebaseFirestore.getInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }

    private void saveUserProfile() {
        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get the user profile data from the EditText fields
        String name = nameEt.getText().toString();
        String address = addressEt.getText().toString();
        String numberString = numberEt.getText().toString();

        // Validate the number field
        if (TextUtils.isEmpty(numberString)) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        long number = Long.parseLong(numberString);

        // Create a user profile object
        UserProfile userProfile = new UserProfile(email, name, address, number);

        // Store the user profile in the Firestore user document
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.set(userProfile)
                .addOnSuccessListener(aVoid -> {
                    // User profile stored successfully
                    Toast.makeText(GetUserDetails.this, "User profile saved successfully", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Failed to store user profile
                    Toast.makeText(GetUserDetails.this, "Failed to save user profile", Toast.LENGTH_SHORT).show();
                });
    }
}
