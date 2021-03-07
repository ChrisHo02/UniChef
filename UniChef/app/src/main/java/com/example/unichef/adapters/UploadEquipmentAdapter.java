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

import java.util.ArrayList;

public class UploadEquipmentAdapter extends RecyclerView.Adapter<UploadEquipmentAdapter.ViewHolder> {
    ArrayList<Equipment> data;
    Context context;

    public UploadEquipmentAdapter(Context ct, ArrayList<Equipment> equipment) {
        data = equipment;
        context = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.temp_upload_tag_item, parent, false);
        return new UploadEquipmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(data.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textView15);
        }
    }
}
