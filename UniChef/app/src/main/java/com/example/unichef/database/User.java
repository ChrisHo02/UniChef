package com.example.unichef.database;

import java.util.ArrayList;

public class User {
    private long id;
    private String name;
    private String email;
    private String username;
    private String password;
    private ArrayList<Recipe> likedRecipes;
    private ArrayList<Recipe> favouriteRecipes;

    public User(){}

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Recipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    public void setFavouriteRecipes(ArrayList<Recipe> favouriteRecipes) {
        this.favouriteRecipes = favouriteRecipes;
    }

    public void addFavouriteRecipe(Recipe recipe){
        favouriteRecipes.add(recipe);
    }

    public ArrayList<Recipe> getLikedRecipes() {
        return likedRecipes;
    }

    public void setLikedRecipes(ArrayList<Recipe> likedRecipes) {
        this.likedRecipes = likedRecipes;
    }

    public void addLikedRecipe(Recipe recipe){
        likedRecipes.add(recipe);
    }
}
