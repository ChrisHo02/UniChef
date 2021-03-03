package com.example.unichef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Ingredient;

import java.util.ArrayList;

public class UploadIngredientsAdapter extends RecyclerView.Adapter<UploadIngredientsAdapter.ViewHolder> {

    ArrayList<Ingredient> data;
    Context context;

    public UploadIngredientsAdapter(Context ct, ArrayList<Ingredient> ingredients) {
        data = ingredients;
        context = ct;
    }

    @NonNull
    @Override
    public UploadIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.upload_ingredient_item, parent, false);
        return new UploadIngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadIngredientsAdapter.ViewHolder holder, int position) {
        holder.ingredient_txt.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient_txt = itemView.findViewById(R.id.ingredient_txt);
        }
    }
}
