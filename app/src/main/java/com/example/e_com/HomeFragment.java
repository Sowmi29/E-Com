package com.example.e_com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategorySelectedListener {

    private ArrayList<CategoryClass> arrayList;
    private RecyclerView rv;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataInitialize();

        categoryAdapter = new CategoryAdapter(getActivity(), arrayList, this);
        rv.setAdapter(categoryAdapter);

        return view;
    }

    private void dataInitialize() {
        arrayList = new ArrayList<>();
        arrayList.add(new CategoryClass("Women Fashion", R.drawable.woman));
        arrayList.add(new CategoryClass("Men Fashion", R.drawable.man));
        arrayList.add(new CategoryClass("Watches", R.drawable.watch));
        arrayList.add(new CategoryClass("Pet", R.drawable.pets));
        arrayList.add(new CategoryClass("Phone", R.drawable.phone));
        arrayList.add(new CategoryClass("Kitchen Utilities", R.drawable.kitchen));
        // Add more categories as needed
    }

    @Override
    public void onCategorySelected(String categoryName) {
        // Handle the selected category name here
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }
}
