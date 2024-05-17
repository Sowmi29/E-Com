package com.example.e_com;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartItems;
    private FirebaseFirestore db;
    private String userId;
    private Button buyNow;

    public CartFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Retrieve the current user ID
        userId = getCurrentUserId();

        // Create an empty ArrayList to hold the cart items
        cartItems = new ArrayList<>();

        // Initialize the CartAdapter
        cartAdapter = new CartAdapter(getActivity(), cartItems);

        // Fetch the cart items from Firestore
        fetchCartItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        buyNow = view.findViewById(R.id.buyNowButton);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cartAdapter);

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the total price of the products in the cart
                double totalPrice = calculateTotalPrice();

                // Delete all products from the cart
                deleteAllCartItems();

                // Show a toast message with the order details
                String message = "Order successful! Total price: $" + totalPrice;
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void fetchCartItems() {
        if (userId != null) {
            // Create a reference to the "cart" collection for the current user
            CollectionReference cartRef = db.collection("users").document(userId).collection("cart");

            cartRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }

                    cartItems.clear();

                    for (DocumentSnapshot document : value.getDocuments()) {
                        Product product = document.toObject(Product.class);
                        cartItems.add(product);
                    }

                    cartAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;

        for (Product product : cartItems) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }

    private void deleteAllCartItems() {
        if (userId != null) {
            // Create a reference to the "cart" collection for the current user
            CollectionReference cartRef = db.collection("users").document(userId).collection("cart");

            cartRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        document.getReference().delete();
                    }
                } else {
                    Log.e("Firestore error", "Error deleting cart items: " + task.getException());
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
