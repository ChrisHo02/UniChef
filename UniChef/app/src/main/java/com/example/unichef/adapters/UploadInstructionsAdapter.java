package com.example.unichef.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;

import java.util.ArrayList;

public class UploadInstructionsAdapter extends RecyclerView.Adapter<UploadInstructionsAdapter.ViewHolder> {

    ArrayList<Instruction> data;
    Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UploadInstructionsAdapter(Context ct, ArrayList<Instruction> instructions) {
        data = instructions;
        context = ct;
    }

    @NonNull
    @Override
    public UploadInstructionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.upload_instruction_item, parent, false);
        return new UploadInstructionsAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadInstructionsAdapter.ViewHolder holder, int position) {
        holder.instruction_txt.setText(data.get(position).getInstruction());
        int stepInt = data.indexOf(data.get(position)) + 1;
        String step = stepInt + ". ";
        holder.step_txt.setText(step);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instruction_txt;
        TextView step_txt;
        ImageButton deleteInstruction;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            instruction_txt = itemView.findViewById(R.id.instruction_textView);
            step_txt = itemView.findViewById(R.id.step_textView);
            deleteInstruction = itemView.findViewById(R.id.deleteInstruction_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            deleteInstruction.setOnClickListener(new View.OnClickListener() {
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
