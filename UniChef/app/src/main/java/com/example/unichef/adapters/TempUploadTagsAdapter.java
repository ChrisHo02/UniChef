package com.example.unichef.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;

public class TempUploadTagsAdapter extends RecyclerView.Adapter<TempUploadTagsAdapter.ViewHolder> {
    @NonNull
    @Override
    public TempUploadTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TempUploadTagsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag_txt = itemView.findViewById(R.id.tag_txt);
        }
    }
}
