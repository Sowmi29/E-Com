package com.example.e_com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private TextView categoryTextView;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private MyAdapter myAdapter;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        categoryTextView = findViewById(R.id.categoryTextView);

        // Retrieve the extra from the intent
        Intent intent = getIntent();
        String categoryName = null;
        if (intent != null) {
            categoryName = intent.getStringExtra("categoryName");
            if (categoryName != null) {
                categoryTextView.setText(categoryName);
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(ProductActivity.this, productArrayList);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Add the product to the cart
                addToCart(product);
            }
        });

        recyclerView.setAdapter(myAdapter);

        fetchProducts(categoryName);
    }

    private void fetchProducts(String categoryName) {
        db.collection(categoryName)
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            Toast.makeText(ProductActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Product product = dc.getDocument().toObject(Product.class);
                                productArrayList.add(product);
                            }
                        }

                        myAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
    }

    private void addToCart(Product product) {
        // Retrieve the current user ID
        String userId = getCurrentUserId();

        if (userId != null) {
            // Create a reference to the "cart" collection for the current user
            CollectionReference cartRef = db.collection("users").document(userId).collection("cart");

            // Add the product to the cart collection
            cartRef.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(ProductActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProductActivity.this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private String getCurrentUserId() {
        // Replace with your own logic to retrieve the current user ID
        // For example, if you are using Firebase Authentication, you can get the UID of the currently signed-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

}
