package com.example.unichef.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;
import com.example.unichef.ui.recipe.RecipeIngredientsFragment;
import com.example.unichef.ui.recipe.RecipeInstructionsFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int count = 2;
    private ArrayList<Instruction> instructions;
    private ArrayList<Ingredient> ingredients;

    public ViewPagerAdapter(FragmentActivity fa, ArrayList<Instruction> instructions, ArrayList<Ingredient> ingredients) {
        super(fa);
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new RecipeIngredientsFragment(ingredients);
                break;
            case 1:
                fragment = new RecipeInstructionsFragment(instructions);
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
