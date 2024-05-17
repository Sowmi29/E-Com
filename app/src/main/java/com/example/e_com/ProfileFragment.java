package com.example.e_com;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    private TextView nameTextView, addressTextView, numberTextView;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = view.findViewById(R.id.nameTextView);
        addressTextView = view.findViewById(R.id.addressTextView);
        numberTextView = view.findViewById(R.id.numberTextView);

        db = FirebaseFirestore.getInstance();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        String name = documentSnapshot.getString("name");
                        String address = documentSnapshot.getString("address");
                        String number = documentSnapshot.getLong("number").toString();

                        nameTextView.setText(name);
                        addressTextView.setText(address);
                        numberTextView.setText(number);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });

        return view;
    }
}
