package com.example.unichef.database;

public class LikedRecipe {
    private int userId;
    private int recipeId;

    public LikedRecipe(int userId, int recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
