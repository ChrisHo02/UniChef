package com.example.unichef.ui.home;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.LoginActivity;
import com.example.unichef.R;
import com.example.unichef.ViewRecipeActivity;
import com.example.unichef.database.Equipment;
import com.example.unichef.database.FirebaseHelper;
import com.example.unichef.database.Ingredient;
import com.example.unichef.database.Instruction;
import com.example.unichef.database.Recipe;
import com.example.unichef.database.RecipeGenerator;
import com.example.unichef.database.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<Recipe> someRecipes;
    ListView listView;
    String recipeTitle[] = {"Spaghetti", "Lasagne", "Turds with Cream", "Curry", "Avocado", "Fish", "Toilet Finder", "Pizza"};
    String recipeDescription[] = {"Noodles", "Pasta", "Cream isn't fresh. It isn't cream either.", "It'll burn both holes.", "The right technique for binning this...", "It reminds me of he...", "A cool idea to revolutioni...", "Insert funny math joke about Pi."};
    int size[] = {1, 0, 2, 0, 1, 1, 2, 0};

    private ArrayList<Tag> tags;

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

        //DBHelper db = new DBHelper(this.getContext(), null, null, 1);
        //someRecipes = db.getRecipes(db.getReadableDatabase(), 10);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }else{
            DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference("recipes");
            FirebaseHelper helper = new FirebaseHelper();
            tags = helper.getAllTags();
        }

        ChipGroup chipGroup = root.findViewById(R.id.chipGroup);
        for (Tag tag : tags){
            Chip categoryChip = new Chip(container.getContext());
            categoryChip.setText(tag.getName());
            chipGroup.addView(categoryChip);
        }

        listView = root.findViewById(R.id.listView);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity(), ViewRecipeActivity.class)));

        MyAdapter adapter = new MyAdapter(getActivity(), recipeTitle, recipeDescription);
        listView.setAdapter(adapter);

        return root;
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String m_recipeTitle[];
        String m_recipeDescription[];

        MyAdapter (Context c, String title[], String description[]){
            super(c, R.layout.med_recipe_item, R.id.textView1, title);
            this.context = c;
            this.m_recipeTitle = title;
            this.m_recipeDescription = description;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View row = layoutInflater.inflate(R.layout.med_recipe_item, parent, false);

            View row = null;
            if (size[position] == 1){
                row = layoutInflater.inflate(R.layout.med_recipe_item, parent, false);
            } else if (size[position] == 2){
                row = layoutInflater.inflate(R.layout.large_recipe_item, parent, false);
            } else {
                row = layoutInflater.inflate(R.layout.smol_recipe_item, parent, false);
            }

            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDescription = row.findViewById(R.id.textView2);

            myTitle.setText(recipeTitle[position]);
            myDescription.setText(recipeDescription[position]);

            return row;
        }
    }
}