package com.example.unichef.database;

public class RecipeIngredients {
    private int ingredientsId;
    private int recipeId;
    private int quantity;

    public RecipeIngredients(int ingredientsId, int recipeId, int quantity) {
        this.ingredientsId = ingredientsId;
        this.recipeId = recipeId;
        this.quantity = quantity;
    }

    public int getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(int ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
