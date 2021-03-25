package com.example.unichef.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Equipment;

import java.util.ArrayList;

public class UploadEquipmentAdapter extends RecyclerView.Adapter<UploadEquipmentAdapter.ViewHolder> {
    ArrayList<Equipment> data;
    Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UploadEquipmentAdapter(Context ct, ArrayList<Equipment> equipment) {
        data = equipment;
        context = ct;
    }

    @NonNull
    @Override
    public UploadEquipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.temp_upload_tag_item, parent, false);
        return new UploadEquipmentAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadEquipmentAdapter.ViewHolder holder, int position) {
        holder.txt.setText(data.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageButton deleteEquipment;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txt = itemView.findViewById(R.id.textView15);
            deleteEquipment = itemView.findViewById(R.id.delete_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            deleteEquipment.setOnClickListener(new View.OnClickListener() {
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
}
