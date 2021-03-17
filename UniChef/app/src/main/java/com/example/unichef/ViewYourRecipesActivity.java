package com.example.unichef;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unichef.adapters.RecipeAdapter;
import com.example.unichef.database.FirebaseHelper;
import com.example.unichef.database.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Hashtable;

public class ViewYourRecipesActivity extends AppCompatActivity {

    private ListView listView;

    private RecipeAdapter recipeAdapter;
    private Hashtable<String, Integer> recipePositions;

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_recipes);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        listView = findViewById(R.id.listView);
        recipePositions = new Hashtable<>();
        initializeRecipeAdapter();
        updateRecipeAdapter();
    }

    private void initializeRecipeAdapter(){
        RecipeAdapter adapter = new RecipeAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(this, ViewRecipeActivity.class).putExtra("Recipe", adapter.getItem(position))));
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            final CharSequence[] options = {"Edit", "Delete", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewYourRecipesActivity.this);
            builder.setTitle("Select an Option");
            builder.setItems(options, (dialog, i) -> {
                switch (options[i].toString()) {
                    case "Edit":
                        editRecipe(position);
                        break;
                    case "Delete":
                        deleteRecipe(position);
                        break;
                    case "Cancel":
                        dialog.dismiss();
                        break;
                }
            });
            builder.show();
            return true;
        });
        recipeAdapter = adapter;
    }

    private void editRecipe(int position){
        //This will add the recipe to the users saved recipes. Need to implement edit later.
        FirebaseHelper helper = new FirebaseHelper();
        helper.addSavedRecipe(user.getUid(), recipeAdapter.getItem(position));
    }

    private void deleteRecipe(int position){
        FirebaseHelper helper = new FirebaseHelper();
        helper.deleteRecipe(recipeAdapter.getItem(position));
        recipeAdapter.remove(recipeAdapter.getItem(position));
        recipeAdapter.notifyDataSetChanged();
    }

    private void updateRecipeAdapter(){
        mDatabase.child("recipes").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if (recipe.getCreatorId().equals(user.getUid())){
                    recipeAdapter.add(recipe);
                    recipeAdapter.notifyDataSetChanged();
                    recipePositions.put(snapshot.getKey(), recipeAdapter.getPosition(recipe));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if (recipe.getCreatorId().equals(user.getUid())){
                    int position = recipePositions.get(snapshot.getKey());
                    Recipe oldRecipe = recipeAdapter.getOriginalItem(position);
                    recipeAdapter.remove(oldRecipe);
                    recipeAdapter.add(recipe);
                    recipeAdapter.notifyDataSetChanged();
                    int newPosition = recipeAdapter.getPosition(recipe);
                    recipePositions.put(snapshot.getKey(), newPosition);
                }
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
}
