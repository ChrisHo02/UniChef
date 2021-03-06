package com.example.unichef.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.unichef.ui.recipe.RecipeIngredientsFragment;
import com.example.unichef.ui.recipe.RecipeInstructionsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int count = 2;

    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new RecipeIngredientsFragment();
                break;
            case 1:
                fragment = new RecipeInstructionsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
