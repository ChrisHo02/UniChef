package com.example.unichef.database;

public class RecipeEquipment {
    private int equipmentId;
    private int recipeId;

    public RecipeEquipment(int equipmentId, int recipeId) {
        this.equipmentId = equipmentId;
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
}
