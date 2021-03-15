package com.example.unichef.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.R;
import com.example.unichef.ViewRecipeActivity;
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

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<Recipe> someRecipes;
    private ListView listView;
    int size[] = {1, 0, 2, 0, 1, 1, 2, 0};

    private ViewGroup container;

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

        Query query = mDatabase.child("recipes").limitToLast(15);
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
        return root;
    }

    private void updateTags(ChipGroup chipGroup){
        mDatabase.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tagSnapshot : snapshot.getChildren()){
                    Tag tag = new Tag(tagSnapshot.getValue().toString());
                    Chip categoryChip = new Chip(container.getContext());
                    categoryChip.setText(tag.getName());
                    chipGroup.addView(categoryChip);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}