package com.example.unichef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Ingredient;

import java.util.ArrayList;

public class UploadIngredientsAdapter extends RecyclerView.Adapter<UploadIngredientsAdapter.ViewHolder> {

    ArrayList<Ingredient> data;
    Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UploadIngredientsAdapter(Context ct, ArrayList<Ingredient> ingredients) {
        data = ingredients;
        context = ct;

    }

    @NonNull
    @Override
    public UploadIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.upload_ingredient_item, parent, false);
        return new UploadIngredientsAdapter.ViewHolder(view, mListener);
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
        ImageButton deleteIngredient;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            ingredient_txt = itemView.findViewById(R.id.ingredient_txt);
            deleteIngredient = itemView.findViewById(R.id.deleteIngredient_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        // Add whatever you want to do when removing an Item
    }
}
