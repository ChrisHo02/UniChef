package com.example.unichef.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public UploadInstructionsAdapter(Context ct, ArrayList<Instruction> instructions) {
        data = instructions;
        context = ct;
    }

    @NonNull
    @Override
    public UploadInstructionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.instruction_item, parent, false);
        return new UploadInstructionsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UploadInstructionsAdapter.ViewHolder holder, int position) {
        holder.instruction_txt.setText(data.get(position).getInstruction());
        holder.step_txt.setText(""+data.get(position).getStep()+". ");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instruction_txt;
        TextView step_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instruction_txt = itemView.findViewById(R.id.textView1);
            step_txt = itemView.findViewById(R.id.textView14);
        }
    }
}
