package com.example.unichef.database;

public class Comment {
    private User user;
    private Recipe recipe;
    private String comment;
    private String date;

    public Comment(User user, Recipe recipe, String comment, String date){
        this.user = user;
        this.recipe = recipe;
        this.comment = comment;
        this.date = date;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
