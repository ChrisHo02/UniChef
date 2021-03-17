package com.example.unichef.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unichef.R;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        ImageView image = v.findViewById(R.id.image);

        assert recipe != null;
        title.setText(recipe.getTitle());
        description.setText(recipe.getDescription());
        Picasso.get().load(recipe.getImageUrl()).into(image);

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
                    results.values = filteredRecipes;
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
                results.values = finalRecipes;
                results.count = finalRecipes.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredRecipes = (ArrayList<Recipe>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
