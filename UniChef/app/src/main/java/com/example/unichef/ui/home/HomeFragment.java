package com.example.unichef.ui.home;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.ProfanityFilter;
import com.example.unichef.R;
import com.example.unichef.ViewRecipeActivity;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.FirebaseHelper;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewGroup container;

    private ListView listView;
    private SearchView searchView;

    private ChipGroup chipGroup;

    private RecipeAdapter recipeAdapter;

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        this.container = container;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        if (user == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        chipGroup = root.findViewById(R.id.chipGroup);
        updateTags(chipGroup);

        listView = root.findViewById(R.id.listView);
        initializeRecipeAdapter();
        updateRecipeAdapter();

        searchView = root.findViewById(R.id.searchView);
        searchFilter();

        return root;
    }

    private void searchFilter(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")){
                    filterRecipeAdapter();
                }
                return false;
            }
        });
    }

    private void updateTags(ChipGroup chipGroup){
        mDatabase.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tagSnapshot : snapshot.getChildren()){
                    Tag tag = new Tag(tagSnapshot.getKey());
                    Chip categoryChip = new Chip(container.getContext());
                    categoryChip.setText(tag.getName());
                    categoryChip.setCheckable(true);
                    categoryChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        filterRecipeAdapter();
                    });
                    chipGroup.addView(categoryChip);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initializeRecipeAdapter(){
        RecipeAdapter adapter = new RecipeAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity(), ViewRecipeActivity.class).putExtra("Recipe", adapter.getItem(position))));
        recipeAdapter = adapter;
    }

    private void updateRecipeAdapter(){
        mDatabase.child("recipes").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                recipeAdapter.add(recipe);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                for (Recipe r : recipeAdapter.getItems()){
                    if (r.getImageUrl().equals("Uploading")){
                        recipeAdapter.remove(r);
                    }
                }
                recipeAdapter.add(recipe);
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterRecipeAdapter(){
        StringBuilder filterString = new StringBuilder();
        List<Integer> chipIds = (ArrayList<Integer>) chipGroup.getCheckedChipIds();
        for (Integer chipId : chipIds){
            Chip chip = chipGroup.findViewById(chipId);
            filterString.append("<t>").append(chip.getText().toString()).append("</t>");
        }
        String searchString = searchView.getQuery().toString();
        if (!searchString.isEmpty()){
            filterString.append("<s>").append(searchView.getQuery().toString()).append("</s>");
        }
        recipeAdapter.getFilter().filter(filterString);
    }

    private static class RecipeAdapter extends ArrayAdapter<Recipe> implements Filterable {
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

        public ArrayList<Recipe> getItems(){
            return originalRecipes;
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
                            for (Tag tag : filterableRecipe.getTags()){
                                if (tag.getName().equals(filterTag)){
                                    filteredRecipes.add(filterableRecipe);
                                    break checkedRecipe;
                                }
                            }
                        }
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
}