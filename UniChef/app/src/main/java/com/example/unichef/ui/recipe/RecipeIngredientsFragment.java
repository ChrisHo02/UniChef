package com.example.unichef.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unichef.R;
import com.example.unichef.adapters.IngredientAdapter;
import com.example.unichef.database.Ingredient;

import java.util.ArrayList;

public class RecipeIngredientsFragment extends Fragment {
    private ArrayList<Ingredient> ingredients;

    public RecipeIngredientsFragment(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        IngredientAdapter adapter = new IngredientAdapter(ingredients);
        RecyclerView recyclerView = view.findViewById(R.id.equipment_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
