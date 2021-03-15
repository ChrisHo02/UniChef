package com.example.unichef.database;

public class RecipeTags {
    private int tagId;
    private int recipeId;

    public RecipeTags(int tagId, int recipeId) {
        this.tagId = tagId;
        this.recipeId = recipeId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
