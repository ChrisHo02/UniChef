package com.example.unichef;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.unichef.adapters.ViewPagerAdapter;
import com.example.unichef.database.Recipe;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ViewRecipeActivity extends AppCompatActivity implements Serializable {

    String[] tabNames = {"Ingredients", "Instructions"};
    private Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        recipe = (Recipe) getIntent().getSerializableExtra("Recipe");

        TextView title = findViewById(R.id.textView);
        title.setText(recipe.getTitle());

        ImageView image = findViewById(R.id.imageView);
        Picasso.get().load(recipe.getImageUrl()).into(image);

        FragmentStateAdapter adapter = new ViewPagerAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabNames[position])).attach();
    }

}
