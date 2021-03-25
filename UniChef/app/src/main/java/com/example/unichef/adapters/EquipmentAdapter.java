package com.example.unichef.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.Instruction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder>{

    private final ArrayList<Equipment> equipment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView1);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public EquipmentAdapter(ArrayList<Equipment> equipment){
        this.equipment = equipment;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.instruction_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewholder, int position) {
        int indexNum = position + 1;
        String index = indexNum + ". ";
        viewholder.getTextView().setText(index + equipment.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return equipment.size();
    }
}
