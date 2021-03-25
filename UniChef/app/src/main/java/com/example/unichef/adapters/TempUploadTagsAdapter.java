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
import com.example.unichef.database.Tag;

import java.util.ArrayList;

public class TempUploadTagsAdapter extends RecyclerView.Adapter<TempUploadTagsAdapter.ViewHolder> {
    ArrayList<Tag> data;
    Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TempUploadTagsAdapter(Context ct, ArrayList<Tag> tag) {
        data = tag;
        context = ct;
    }

    @NonNull
    @Override
    public TempUploadTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.temp_upload_tag_item, parent, false);
        return new TempUploadTagsAdapter.ViewHolder(view, mListener);
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
        ImageButton deleteTag_button;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tag_txt = itemView.findViewById(R.id.textView15);
            deleteTag_button = itemView.findViewById(R.id.delete_button);

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

            deleteTag_button.setOnClickListener(new View.OnClickListener() {
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
