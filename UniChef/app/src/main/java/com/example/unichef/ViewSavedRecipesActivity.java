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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class ViewSavedRecipesActivity extends AppCompatActivity {

    private ListView listView;

    private RecipeAdapter recipeAdapter;
    private Hashtable<String, Integer> recipePositions;

    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_recipes);

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
            final CharSequence[] options = {"Remove", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewSavedRecipesActivity.this);
            builder.setTitle("Select an Option");
            builder.setItems(options, (dialog, i) -> {
                switch (options[i].toString()) {
                    case "Remove":
                        removeRecipe(position);
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

    private void removeRecipe(int position){
        FirebaseHelper helper = new FirebaseHelper();
        helper.removeSavedRecipe(user.getUid(), recipeAdapter.getItem(position));
        recipeAdapter.remove(recipeAdapter.getItem(position));
        recipeAdapter.notifyDataSetChanged();
    }

    private void updateRecipeAdapter(){
        mDatabase.child("users").child(user.getUid()).child("savedRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    mDatabase.child("recipes").child(snap.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Recipe recipe = snapshot.getValue(Recipe.class);
                            if (recipe != null){
                                recipeAdapter.add(recipe);
                                recipeAdapter.notifyDataSetChanged();
                                recipePositions.put(snapshot.getKey(), recipeAdapter.getPosition(recipe));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
