package com.example.unichef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.Tag;

import java.util.ArrayList;

public class TempUploadTagsAdapter extends RecyclerView.Adapter<TempUploadTagsAdapter.ViewHolder> {
    ArrayList<Tag> data;
    Context context;

    public TempUploadTagsAdapter(Context ct, ArrayList<Tag> tag) {
        data = tag;
        context = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.temp_upload_tag_item, parent, false);
        return new TempUploadTagsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempUploadTagsAdapter.ViewHolder holder, int position) {
        holder.tag_txt.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tag_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag_txt = itemView.findViewById(R.id.textView15);
        }
    }
}
