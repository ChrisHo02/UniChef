package com.example.unichef.database;

public class Recipe {
    private int recipeId;
    private int userId;
    private String title;
    private String description;
    private String imageUrl;
    private String dateAdded;
    private int likes;
    private int difficulty;
    private int time;
    private int portions;

    public Recipe(int recipeId, int userId, String title, String description, String imageUrl, String dateAdded, int likes, int difficulty, int time, int portions) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.dateAdded = dateAdded;
        this.likes = likes;
        this.difficulty = difficulty;
        this.time = time;
        this.portions = portions;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }
}
