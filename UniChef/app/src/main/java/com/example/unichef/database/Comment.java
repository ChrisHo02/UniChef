package com.example.unichef.database;

import java.io.Serializable;

public class Comment implements Serializable {
    private String userId;
    private String comment;
    private long date;

    public Comment(){}

    public Comment(String userId, long recipeId, String comment, long date){
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
