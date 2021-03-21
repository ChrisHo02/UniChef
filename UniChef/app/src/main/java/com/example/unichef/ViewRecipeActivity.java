package com.example.unichef;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.unichef.adapters.CommentAdapter;
import com.example.unichef.adapters.RecipeAdapter;
import com.example.unichef.adapters.ViewPagerAdapter;
import com.example.unichef.database.Comment;
import com.example.unichef.database.FirebaseHelper;
import com.example.unichef.database.Recipe;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ViewRecipeActivity extends AppCompatActivity implements Serializable {

    String[] tabNames = {"Ingredients", "Instructions"};
    private Recipe recipe;
    private Button commentButton;
    private EditText commentText;

    private ArrayList<Comment> comments;

    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_recipe);

        recipe = (Recipe) getIntent().getSerializableExtra("Recipe");

        TextView title = findViewById(R.id.textView);
        title.setText(recipe.getTitle());

        ImageView image = findViewById(R.id.imageView);
        Picasso.get().load(recipe.getImageUrl()).into(image);

        FragmentStateAdapter adapter = new ViewPagerAdapter(this, recipe.getInstructions(), recipe.getIngredients());
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabNames[position])).attach();

        user = FirebaseAuth.getInstance().getCurrentUser();

        comments = recipe.getComments();

        ListView listView = findViewById(R.id.commentListView);
        if (comments == null){
            comments = new ArrayList<>();
        }
        CommentAdapter commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);

        commentText = findViewById(R.id.editTextTextPersonName);
        commentButton = findViewById(R.id.submit_comment_btn);
        commentButton.setOnClickListener(v -> {
            if (commentText.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Can't post an empty comment!", Toast.LENGTH_SHORT).show();
            }else{
                Comment comment = new Comment();
                comment.setUserId(user.getUid());
                comment.setComment(commentText.getText().toString());
                comment.setDate(new Date().getTime());

                commentAdapter.add(comment);

                FirebaseHelper helper = new FirebaseHelper();
                helper.postComment(recipe, comments);
                Toast.makeText(getApplicationContext(), "Comment Posted!", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseHelper helper = new FirebaseHelper();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        helper.addSavedRecipe(user.getUid(), recipe);
    }

}
