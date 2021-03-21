package com.example.unichef.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.unichef.R;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeAdapter extends ArrayAdapter<Recipe> implements Filterable {
    private final ArrayList<Recipe> originalRecipes;
    private ArrayList<Recipe> filteredRecipes;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        super(context, 0, recipes);
        this.originalRecipes = recipes;
        this.filteredRecipes = recipes;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){
        Recipe recipe = getItem(position);

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.med_recipe_item, parent, false);
        }
        TextView title = v.findViewById(R.id.textView1);
        TextView description = v.findViewById(R.id.textView2);
        TextView likes = v.findViewById(R.id.likesText);
        TextView difficulty = v.findViewById(R.id.difficultyText);
        TextView time = v.findViewById(R.id.timeText);
        TextView portions = v.findViewById(R.id.portionText);
        TextView equipment = v.findViewById(R.id.equipmentText);
        ImageView image = v.findViewById(R.id.image);
        ChipGroup chipGroup = v.findViewById(R.id.recipeChipGroup);
        chipGroup.removeAllViews();

        assert recipe != null;
        title.setText(recipe.getTitle());
        description.setText(recipe.getDescription());
        likes.setText(" " + recipe.getLikes());
        likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_favorite_border_24, 0, 0, 0);
        difficulty.setText(" " + recipe.getDifficulty() + "/5");
        difficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_speed_24, 0, 0, 0);
        time.setText(" " + recipe.getTime());
        time.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_alarm_24, 0, 0, 0);
        portions.setText(" " + recipe.getPortions());
        portions.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_dinner_dining_24, 0, 0, 0);
        equipment.setText(" " + recipe.getEquipment().size());
        equipment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_restaurant_menu_24, 0, 0, 0);
        Picasso.get().load(recipe.getImageUrl()).into(image);
        for (Tag tag : recipe.getTags()){
            Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.small_chip_style, chipGroup, false);
            chip.setText(tag.getName());
            chipGroup.addView(chip);
        }

        return v;
    }

    @Override
    public int getCount() {
        return filteredRecipes.size();
    }

    @Nullable
    @Override
    public Recipe getItem(int position) {
        return filteredRecipes.get(position);
    }

    public Recipe getOriginalItem(int position){
        return  originalRecipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString();
                FilterResults results = new FilterResults();
                if (filterString.isEmpty()){
                    results.values = originalRecipes;
                    results.count = originalRecipes.size();
                    return results;
                }

                List<String> filterTags = new ArrayList<>();
                Matcher tagMatcher = Pattern.compile("<t>(.*?)</t>").matcher(filterString);
                while (tagMatcher.find()){
                    filterTags.add(tagMatcher.group(1));
                }

                String filterSearch = "";
                Matcher searchMatcher = Pattern.compile("<s>(.*?)</s>").matcher(filterString);
                if (searchMatcher.find()){
                    filterSearch = searchMatcher.group(1).toLowerCase();
                }

                List<String> filters = new ArrayList<>();
                Matcher filterMatcher = Pattern.compile("<f>(.*?)</f>").matcher(filterString);
                while (filterMatcher.find()){
                    filters.add(filterMatcher.group(1));
                }

                if (filterSearch.isEmpty() && filterTags.isEmpty()){
                    sortRecipes(originalRecipes, filters);
                    results.values = originalRecipes;
                    results.count = originalRecipes.size();
                    return results;
                }

                ArrayList<Recipe> filteredRecipes = new ArrayList<>();
                for (Recipe filterableRecipe : originalRecipes){
                    checkedRecipe:
                    for (String filterTag : filterTags){
                        if (filterableRecipe.getTags() != null){
                            for (Tag tag : filterableRecipe.getTags()){
                                if (tag.getName().equals(filterTag)){
                                    filteredRecipes.add(filterableRecipe);
                                    break checkedRecipe;
                                }
                            }
                        }
                    }
                }

                if (filterSearch.isEmpty()){
                    if (filters.isEmpty()){
                        results.values = filteredRecipes;
                    }else{
                        results.values = sortRecipes(filteredRecipes, filters);
                    }
                    results.count = filteredRecipes.size();
                    return results;
                }
                ArrayList<Recipe> finalRecipes = new ArrayList<>();
                if (filteredRecipes.size() > 0){
                    for (Recipe filteredRecipe : filteredRecipes){
                        if (filteredRecipe.getTitle().toLowerCase().contains(filterSearch) || filteredRecipe.getDescription().toLowerCase().contains(filterSearch)){
                            finalRecipes.add(filteredRecipe);
                        }
                    }
                }else{
                    for (Recipe filterableRecipe : originalRecipes){
                        if (filterableRecipe.getTitle().toLowerCase().contains(filterSearch) || filterableRecipe.getDescription().toLowerCase().contains(filterSearch)){
                            finalRecipes.add(filterableRecipe);
                        }
                    }
                }
                if (filters.isEmpty()){
                    results.values = finalRecipes;
                }else{
                    results.values = sortRecipes(finalRecipes, filters);
                }
                results.count = finalRecipes.size();
                return results;
            }

            private ArrayList<Recipe> sortRecipes(ArrayList<Recipe> recipes, List<String> filters){
                for (String filter : filters){
                    switch (filter){
                        case "Difficulty":
                            Comparator<Recipe> difficulty = (r1, r2) -> Integer.compare(r1.getDifficulty(), r2.getDifficulty());
                            Collections.sort(recipes, difficulty);
                            break;
                        case "Time":
                            Comparator<Recipe> time = (r1, r2) -> Integer.compare(r1.getTime(), r2.getTime());
                            Collections.sort(recipes, time);
                            break;
                        case "Rating":
                            Comparator<Recipe> rating = (r1, r2) -> Integer.compare(r1.getLikes(), r2.getLikes());
                            Collections.sort(recipes, rating);
                            break;
                        case "Date Added":
                            Comparator<Recipe> date = (r1, r2) -> Double.compare(r1.getDateAdded(), r2.getDateAdded());
                            Collections.sort(recipes, date);
                            break;
                        case "Portions":
                            Comparator<Recipe> portions = (r1, r2) -> Integer.compare(r1.getPortions(), r2.getPortions());
                            Collections.sort(recipes, portions);
                            break;
                        case "Equipment":
                            Comparator<Recipe> equipment = (r1, r2) -> Integer.compare(r1.getEquipment().size(), r2.getEquipment().size());
                            Collections.sort(recipes, equipment);
                            break;
                    }
                }
                return recipes;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredRecipes = (ArrayList<Recipe>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
