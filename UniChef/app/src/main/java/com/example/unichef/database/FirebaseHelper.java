package com.example.unichef.database;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class FirebaseHelper {
    private final DatabaseReference mDatabase;
    private final StorageReference mStorage;
    private final FirebaseAuth mAuth;

    public FirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mStorage = FirebaseStorage.getInstance("gs://unichef-f6056.appspot.com").getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public String uploadRecipe(Recipe recipe){
        String recipeId = mDatabase.child("recipes").push().getKey();
        mDatabase.child("recipes").child(recipeId).setValue(recipe);
        uploadRecipeTags(recipe);
        uploadRecipeImage(recipe, recipeId);
        return recipeId;
    }

    public void uploadRecipeTags(Recipe recipe){
        for(Tag tag : recipe.getTags()){
            mDatabase.child("tags").child(tag.getName()).setValue(tag.getName());
        }
    }

    public void uploadRecipeImage(Recipe recipe, String recipeId){
        StorageReference recipeRef = mStorage.child("recipes/" + new Date().getTime() + ".png");
        Uri file = Uri.fromFile(new File(recipe.getImageUrl()));
        UploadTask uploadTask = recipeRef.putFile(file);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> downloadUrl = recipeRef.getDownloadUrl();
            downloadUrl.addOnSuccessListener(uri -> {
                String imageReference = uri.toString();
                mDatabase.child("recipes").child(recipeId).child("imageUrl").setValue(imageReference);
                recipe.setImageUrl(imageReference);
            });
        });
    }

    public ArrayList<Tag> getAllTags(){
        ArrayList<Tag> tags = new ArrayList<>();
        mDatabase.child("tags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Tag tag = new Tag(snapshot.getValue().toString());
                tags.add(tag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return tags;
    }
}