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

import org.apache.commons.text.WordUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class FirebaseHelper {
    private final DatabaseReference mDatabase;
    private final StorageReference mStorage;

    public FirebaseHelper(){
        mDatabase = FirebaseDatabase.getInstance("https://unichef-f6056-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mStorage = FirebaseStorage.getInstance("gs://unichef-f6056.appspot.com").getReference();
    }

    public String uploadRecipe(Recipe recipe){
        updateTags(recipe);
        String recipeId = mDatabase.child("recipes").push().getKey();
        assert recipeId != null;
        mDatabase.child("recipes").child(recipeId).setValue(recipe);
        uploadRecipeTags(recipe, recipeId);
        uploadRecipeImage(recipe, recipeId);
        return recipeId;
    }

    private void updateTags(Recipe recipe){
        ArrayList<Tag> updatedTags = new ArrayList<>();
        for (int i = 0; i < recipe.getTags().size(); i++){
            String tagName = recipe.getTags().get(i).getName();
            updatedTags.add(new Tag(WordUtils.capitalizeFully(tagName)));
        }
        recipe.setTags(updatedTags);
    }

    public void uploadRecipeTags(Recipe recipe, String recipeId){
        for(Tag tag : recipe.getTags()){
            String tagName = WordUtils.capitalizeFully(tag.getName());
            mDatabase.child("tags").child(tagName).child(recipeId).setValue(true);
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

    public void uploadProfilePicture(String filePath, String userId){
        StorageReference profileRef = mStorage.child("profile/" + new Date().getTime() + ".png");
        Uri file = Uri.fromFile(new File(filePath));
        UploadTask uploadTask = profileRef.putFile(file);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> downloadUrl = profileRef.getDownloadUrl();
            downloadUrl.addOnSuccessListener(uri -> {
                String imageReference = uri.toString();
                mDatabase.child("users").child(userId).child("profileImage").setValue(imageReference);
            });
        });
    }

    public void deleteRecipe(Recipe recipe){
        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("recipes").child(snap.getKey()).removeValue();
                        deleteImage(recipe.getImageUrl());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void deleteImage(String imageUrl){
        StorageReference imageRef = FirebaseStorage.getInstance("gs://unichef-f6056.appspot.com").getReferenceFromUrl(imageUrl);
        imageRef.delete();
    }

    public void addSavedRecipe(String userId, Recipe recipe){
        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(userId).child("savedRecipes").child(snap.getKey()).setValue(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void removeSavedRecipe(String userId, Recipe recipe){
        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("users").child(userId).child("savedRecipes").child(snap.getKey()).removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void postComment(Recipe recipe, ArrayList<Comment> comments){
        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("recipes").child(snap.getKey()).child("comments").setValue(comments);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void likeRecipe(String userId, Recipe recipe){
        mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Recipe tempRecipe = snap.getValue(Recipe.class);
                    if (tempRecipe.getDateAdded() == recipe.getDateAdded() && tempRecipe.getCreatorId().equals(recipe.getCreatorId())){
                        mDatabase.child("recipes").child(snap.getKey()).child("likes").setValue(tempRecipe.getLikes() + 1);
                        mDatabase.child("users").child(userId).child("likedRecipes").child(snap.getKey()).setValue(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
