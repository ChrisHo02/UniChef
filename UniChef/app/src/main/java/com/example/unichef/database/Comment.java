package com.example.unichef.database;

public class Comment {
    private User user;
    private long recipeId;
    private String comment;
    private long date;

    public Comment(){}

    public Comment(User user, long recipeId, String comment, long date){
        this.user = user;
        this.recipeId = recipeId;
        this.comment = comment;
        this.date = date;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}
