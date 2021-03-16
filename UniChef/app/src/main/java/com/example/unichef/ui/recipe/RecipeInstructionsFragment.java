package com.example.unichef.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.adapters.InstructionAdapter;
import com.example.unichef.database.Instruction;

import java.util.ArrayList;


public class RecipeInstructionsFragment extends Fragment {
    private ArrayList<Instruction> instructions;

    public RecipeInstructionsFragment(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_recipe_instructions, container, false);

        InstructionAdapter adapter = new InstructionAdapter(instructions);
        RecyclerView recyclerView = view.findViewById(R.id.instructions_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
