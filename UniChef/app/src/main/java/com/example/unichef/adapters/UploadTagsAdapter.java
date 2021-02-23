package com.example.unichef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;

public class UploadTagsAdapter extends RecyclerView.Adapter<UploadTagsAdapter.ViewHolder> {

    String data[];
    Context context;

    public UploadTagsAdapter(Context ct, String tags[]) {
        data = tags;
        context = ct;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.upload_tag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ingredient_txt.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient_txt = itemView.findViewById(R.id.tag_txt);
        }
    }
}
