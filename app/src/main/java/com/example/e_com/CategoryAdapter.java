package com.example.e_com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryClass> arrayList;
    private OnCategorySelectedListener listener;

    public CategoryAdapter(Context context, ArrayList<CategoryClass> arrayList, OnCategorySelectedListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        CategoryClass categoryClass = arrayList.get(position);
        holder.text.setText(categoryClass.getName());
        holder.img.setImageResource(categoryClass.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = arrayList.get(holder.getAdapterPosition()).getName();
                listener.onCategorySelected(categoryName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewCategory);
            text = itemView.findViewById(R.id.textViewCategoryName);
        }
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(String categoryName);
    }
}
