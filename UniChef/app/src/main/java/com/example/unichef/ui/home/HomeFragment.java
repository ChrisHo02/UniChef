package com.example.unichef.ui.home;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.R;
import com.example.unichef.ViewRecipeActivity;
import com.example.unichef.adapters.RecipeAdapter;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewGroup container;

    private ListView listView;
    private SearchView searchView;

    private ChipGroup chipGroup;

    private Hashtable<String, Integer> recipePositions;
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
        recipePositions = new Hashtable<>();
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
                filterRecipeAdapter();
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
                recipePositions.put(snapshot.getKey(), recipeAdapter.getPosition(recipe));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                int position = recipePositions.get(snapshot.getKey());
                Recipe oldRecipe = recipeAdapter.getOriginalItem(position);
                recipeAdapter.remove(oldRecipe);
                recipeAdapter.add(recipe);
                recipeAdapter.notifyDataSetChanged();
                int newPosition = recipeAdapter.getPosition(recipe);
                recipePositions.put(snapshot.getKey(), newPosition);
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
}