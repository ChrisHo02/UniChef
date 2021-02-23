package com.example.unichef.database;

public class Comment {
    private int commentId;
    private int userId;
    private int recipeId;
    private String comment;
    private String date;

    public Comment(){}

    public Comment(int commentId, int userId, int recipeId, String comment, String date){
        this.commentId = commentId;
        this.userId = userId;
        this.recipeId = recipeId;
        this.comment = comment;
        this.date = date;
    }

    public String getComment(){
        return this.comment;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
