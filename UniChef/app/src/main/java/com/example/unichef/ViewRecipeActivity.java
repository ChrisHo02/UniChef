package com.example.unichef;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ViewRecipeActivity extends AppCompatActivity implements Serializable {

    String[] tabNames = {"Ingredients", "Instructions", "Equipment"};
    private Recipe recipe;
    private Button commentButton;
    private ImageButton likeButton;
    private ImageButton saveButton;
    private EditText commentText;

    private ArrayList<Comment> comments;

    private FirebaseUser user;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_view_recipe);

        recipe = (Recipe) getIntent().getSerializableExtra("Recipe");

        TextView title = findViewById(R.id.textView);
        title.setText(recipe.getTitle());

        TextView description = findViewById(R.id.description);
        description.setText(recipe.getDescription());

        TextView time = findViewById(R.id.recipe_time);
        time.setText("Time: " + recipe.getTime() + "minutes");

        TextView portion = findViewById(R.id.recipe_rating);
        portion.setText("Portions: " + recipe.getPortions());

        TextView difficulty = findViewById(R.id.difficulty_rating);
        difficulty.setText("Difficulty: " + recipe.getDifficulty() + "/5");

        TextView likes = findViewById(R.id.likeText);
        likes.setText(String.valueOf(recipe.getLikes()));

        ImageView image = findViewById(R.id.imageView);
        Picasso.get().load(recipe.getImageUrl()).into(image);

        FragmentStateAdapter adapter = new ViewPagerAdapter(this, recipe.getInstructions(), recipe.getIngredients(), recipe.getEquipment());
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabNames[position])).attach();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        comments = recipe.getComments();

        ListView listView = findViewById(R.id.commentListView);
        if (comments == null){
            comments = new ArrayList<>();
        }
        CommentAdapter commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);

        FirebaseHelper helper = new FirebaseHelper();

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

                helper.postComment(recipe, comments);
                commentText.getText().clear();
                Toast.makeText(getApplicationContext(), "Comment Posted!", Toast.LENGTH_SHORT).show();
            }
        });

        likeButton = findViewById(R.id.like_btn_recipe);

        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(user.getUid()).child("likedRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean saved = false;
                                for (DataSnapshot s : snapshot.getChildren()){
                                    if (snap.getKey().equals(s.getKey())){
                                        saved = true;
                                        break;
                                    }
                                }
                                if (saved){
                                    likeButton.setColorFilter(Color.rgb(98,0,238));
                                }else{
                                    likeButton.setColorFilter(Color.BLACK);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        likeButton.setOnClickListener(v -> mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(user.getUid()).child("likedRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean saved = false;
                                for (DataSnapshot s : snapshot.getChildren()){
                                    if (snap.getKey().equals(s.getKey())){
                                        saved = true;
                                        break;
                                    }
                                }
                                int currentLikes = recipe.getLikes();
                                if (saved){
                                    likeButton.setColorFilter(Color.BLACK);
                                    helper.removeLikedRecipe(user.getUid(), recipe);
                                    recipe.setLikes(currentLikes - 1);
                                    likes.setText(String.valueOf(recipe.getLikes()));
                                }else{
                                    likeButton.setColorFilter(Color.rgb(98,0,238));
                                    helper.addLikedRecipe(user.getUid(), recipe);
                                    recipe.setLikes(currentLikes + 1);
                                    likes.setText(String.valueOf(recipe.getLikes()));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }));

        saveButton = findViewById(R.id.fav_btn_recipe);

        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(user.getUid()).child("savedRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean saved = false;
                                for (DataSnapshot s : snapshot.getChildren()){
                                    if (snap.getKey().equals(s.getKey())){
                                        saved = true;
                                        break;
                                    }
                                }
                                if (saved){
                                    saveButton.setColorFilter(Color.rgb(98,0,238));
                                }else{
                                    saveButton.setColorFilter(Color.BLACK);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        saveButton.setOnClickListener(v -> mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(user.getUid()).child("savedRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean saved = false;
                                for (DataSnapshot s : snapshot.getChildren()){
                                    if (snap.getKey().equals(s.getKey())){
                                        saved = true;
                                        break;
                                    }
                                }
                                if (saved){
                                    saveButton.setColorFilter(Color.BLACK);
                                    helper.removeSavedRecipe(user.getUid(), recipe);
                                }else{
                                    saveButton.setColorFilter(Color.rgb(98,0,238));
                                    helper.addSavedRecipe(user.getUid(), recipe);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }));
    }

}
