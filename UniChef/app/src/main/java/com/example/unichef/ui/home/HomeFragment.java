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

import com.bumptech.glide.Glide;
import com.example.unichef.LoginActivity;
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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewGroup container;

    private ListView listView;
    private SearchView searchView;

    private RecipeAdapter recipeAdapter;
    private FirebaseListAdapter<Recipe> firebaseRecipeAdapter;

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //This was default code written when I loaded up the project.
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        this.container = container;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        if (user == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        ChipGroup chipGroup = root.findViewById(R.id.chipGroup);
        updateTags(chipGroup);

        listView = root.findViewById(R.id.listView);
        Query query = mDatabase.child("recipes").limitToLast(25);
        updateRecipeAdapter(query);

        searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (chipGroup.getCheckedChipIds().size() == 0){
                    String searchPhrase = WordUtils.capitalizeFully(newText);
                    Query query = mDatabase.child("recipes").orderByChild("title").startAt(searchPhrase).endAt(searchPhrase + "\uf8ff").limitToLast(25);
                    updateRecipeAdapter(query);
                }else{
                    recipeAdapter.getFilter().filter(newText);
                }
                //Need to decide whether we use the poor search database, or good search but no auto updates
                return false;
            }
        });

        return root;
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
                        if (recipeAdapter == null){
                            initializeNormalRecipeAdapter();
                        }else{
                            recipeAdapter.clear();
                            recipeAdapter.notifyDataSetChanged();
                        }
                        ArrayList<Tag> checkedTags = new ArrayList<>();
                        for (Integer chipId : chipGroup.getCheckedChipIds()){
                            Chip checkedChip = chipGroup.findViewById(chipId);
                            checkedTags.add(new Tag(checkedChip.getText().toString()));
                        }
                        if (checkedTags.size() == 0){
                            listView.setAdapter(firebaseRecipeAdapter);
                        }else{
                            listView.setAdapter(recipeAdapter);
                            recipeAdapter.notifyDataSetChanged();
                        }
                        for (Tag checkedTag : checkedTags){
                            Query query = mDatabase.child("tags").child(checkedTag.getName()).limitToLast(25);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot recipe : snapshot.getChildren()){
                                        if (recipe.getKey() != null){
                                            Query recipeQuery = mDatabase.child("recipes").child(recipe.getKey());
                                            recipeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    Recipe recipe = snapshot.getValue(Recipe.class);
                                                    recipeAdapter.add(recipe);
                                                    recipeAdapter.getFilter().filter(searchView.getQuery());
                                                    recipeAdapter.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                        }else{
                                            recipeAdapter.clear();
                                            recipeAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    });
                    chipGroup.addView(categoryChip);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateRecipeAdapter(Query query){
        FirebaseListOptions<Recipe> options = new FirebaseListOptions.Builder<Recipe>().setLayout(R.layout.med_recipe_item).setQuery(query, Recipe.class).build();
        FirebaseListAdapter<Recipe> adapter = new FirebaseListAdapter<Recipe>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Recipe model, int position) {
                TextView title = v.findViewById(R.id.textView1);
                TextView description = v.findViewById(R.id.textView2);
                ImageView image = v.findViewById(R.id.image);

                title.setText(model.getTitle());
                description.setText(model.getDescription());
                Picasso.get().load(model.getImageUrl()).into(image);
            }
        };
        listView.setAdapter(adapter);
        adapter.startListening();
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity(), ViewRecipeActivity.class).putExtra("Recipe", adapter.getItem(position))));
        firebaseRecipeAdapter = adapter;
    }

    private void initializeNormalRecipeAdapter(){
        RecipeAdapter adapter = new RecipeAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity(), ViewRecipeActivity.class).putExtra("Recipe", adapter.getItem(position))));
        recipeAdapter = adapter;
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
            Glide.with(this.getContext()).load(recipe.getImageUrl()).into(image);
//            Picasso.get().load(recipe.getImageUrl()).into(image);

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
                    String filterString = constraint.toString().toLowerCase();
                    FilterResults results = new FilterResults();
                    if (filterString.isEmpty()){
                        results.values = originalRecipes;
                        results.count = originalRecipes.size();
                        return results;
                    }
                    final ArrayList<Recipe> recipes = originalRecipes;
                    int count = recipes.size();
                    final ArrayList<Recipe> filteredRecipes = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
                        Recipe filterableRecipe = recipes.get(i);
                        if (filterableRecipe.getTitle().toLowerCase().contains(filterString) || filterableRecipe.getDescription().contains(filterString)) {
                            filteredRecipes.add(recipes.get(i));
                        }
                    }
                    results.values = filteredRecipes;
                    results.count = filteredRecipes.size();
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