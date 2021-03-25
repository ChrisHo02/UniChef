package com.example.unichef.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.adapters.EquipmentAdapter;
import com.example.unichef.database.Equipment;

import java.util.ArrayList;

public class RecipeEquipmentFragment extends Fragment {
    private ArrayList<Equipment> equipment;

    public RecipeEquipmentFragment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_recipe_equipment, container, false);

        EquipmentAdapter adapter = new EquipmentAdapter(equipment);
        RecyclerView recyclerView = view.findViewById(R.id.equipment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}