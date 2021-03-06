package com.example.unichef.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unichef.R;
import com.example.unichef.ViewRecipeActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView listView;
    ArrayList<String> recipeTitles = new ArrayList<>();
    ArrayList<String> recipeDescriptions = new ArrayList<>();
    ArrayList<byte[]> recipeImages = new ArrayList<>();
    int size[] = {1, 0, 2, 0, 1, 1, 2, 0};

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

//        DBHelper db = new DBHelper(this.getContext(), null, null, 1);
//        ArrayList<Recipe> someRecipes = db.getRecipes(db.getReadableDatabase(), 10);
//        for (Recipe recipe : someRecipes){
//            recipeTitles.add(recipe.getTitle());
//            recipeDescriptions.add(recipe.getDescription());
//            recipeImages.add(recipe.getImage());
//        }

//        ChipGroup chipGroup = root.findViewById(R.id.chipGroup);
//        for (Tag tag : db.getAllTags(db.getReadableDatabase())){
//            Chip categoryChip = new Chip(container.getContext());
//            categoryChip.setText(tag.getName());
//            chipGroup.addView(categoryChip);
//        }

        listView = root.findViewById(R.id.listView);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(getActivity(), ViewRecipeActivity.class)));

        MyAdapter adapter = new MyAdapter(getActivity(), recipeTitles, recipeDescriptions);
        listView.setAdapter(adapter);

        return root;
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList<String> m_recipeTitle;
        ArrayList<String> m_recipeDescription;

        MyAdapter (Context c, ArrayList<String> title, ArrayList<String> description){
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
            ImageView myImage = row.findViewById(R.id.image);

//            myTitle.setText(recipeTitles.get(position));
//            myDescription.setText(recipeDescriptions.get(position));
//            Bitmap bitmap = BitmapFactory.decodeByteArray(recipeImages.get(position), 0, recipeImages.get(position).length);
//            myImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, myImage.getWidth(), myImage.getHeight(), false));

            return row;
        }
    }
}