package com.example.unichef.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Debug;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "database.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //comment table
        db.execSQL("CREATE TABLE Comment (commentId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, recipeId INTEGER, comment TEXT, date TEXT, FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //equipment table
        db.execSQL("CREATE TABLE Equipment (equipmentId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        //favourite recipe table
        db.execSQL("CREATE TABLE FavouriteRecipe (userId INTEGER, recipeId Integer, PRIMARY KEY(userId, recipeId), FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //ingredient table
        db.execSQL("CREATE TABLE Ingredient(ingredientId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        //Instruction table
        db.execSQL("CREATE TABLE Instruction(instructionId INTEGER PRIMARY KEY AUTOINCREMENT, recipeId INTEGER, step INTEGER, instruction TEXT, time INT, imageUrl TEXT, FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Liked recipe table
        db.execSQL("CREATE TABLE LikedRecipe(userId INTEGER, recipeId INTEGER, PRIMARY KEY(userId, recipeId), FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe table
        db.execSQL("CREATE TABLE Recipe(recipeId INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, title TEXT, description TEXT, imageUrl TEXT, dateAdded TEXT, likes INTEGER, difficulty INTEGER, time INTEGER, portions INTEGER, FOREIGN KEY(userId) REFERENCES User(userId))");
        //Recipe equipment table
        db.execSQL("CREATE TABLE RecipeEquipment(equipmentId INTEGER, recipeId INTEGER, PRIMARY KEY(equipmentId, recipeId), FOREIGN KEY(equipmentId) REFERENCES Equipment(equipmentId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe ingredients table
        db.execSQL("CREATE TABLE RecipeIngredients(ingredientId INTEGER, recipeId INTEGER, quantity INTEGER, PRIMARY KEY(ingredientId, recipeId), FOREIGN KEY(ingredientId) REFERENCES Ingredient(ingredientId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe tags table
        db.execSQL("CREATE TABLE RecipeTags(tagId INTEGER, recipeId INTEGER, PRIMARY KEY(tagId, recipeId), FOREIGN KEY(tagId) REFERENCES Tag(tagId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Tags table
        db.execSQL("CREATE TABLE Tag(tagId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        //User table
        db.execSQL("CREATE TABLE User(userId INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, username TEXT,  password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addUser(SQLiteDatabase db, User user){
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        long userId = db.insert("User",null, values);
        user.setId(userId);
    }

    public void addComment(SQLiteDatabase db, Comment comment){
        ContentValues values = new ContentValues();
        values.put("userId", comment.getUser().getId());
        values.put("recipeId", comment.getRecipe().getId());
        values.put("comment", comment.getComment());
        values.put("date", comment.getDate());
        db.insert("Comment",null, values);
    }

    public void addEquipment(SQLiteDatabase db, Equipment equipment, long recipeId){
        String equipmentName = equipment.getName();
        String query = "SELECT equipmentId FROM Equipment WHERE name = '" + equipmentName + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("name", equipment.getName());
            long equipmentId = db.insert("Equipment", null, values);

            ContentValues linkValues = new ContentValues();
            linkValues.put("equipmentId", equipmentId);
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeEquipment", null, linkValues);
        }else{
            cursor.moveToFirst();
            ContentValues linkValues = new ContentValues();
            linkValues.put("equipmentId", cursor.getInt(cursor.getColumnIndex("equipmentId")));
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeEquipment", null, linkValues);
        }
        cursor.close();
    }

    public void addIngredient(SQLiteDatabase db, Ingredient ingredient, long recipeId){
        String ingredientName = ingredient.getName();
        String query = "SELECT ingredientId FROM Ingredient WHERE name = '" + ingredientName + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("name", ingredientName);
            long ingredientId = db.insert("Ingredient", null, values);

            ContentValues linkValues = new ContentValues();
            linkValues.put("ingredientId", ingredientId);
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeIngredients", null, linkValues);
        }else{
            cursor.moveToFirst();
            ContentValues linkValues = new ContentValues();
            linkValues.put("ingredientId", cursor.getInt(0));
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeIngredients", null, linkValues);
        }
        cursor.close();
    }

    public void addInstruction(SQLiteDatabase db, Instruction instruction, long recipeId){
        ContentValues values = new ContentValues();
        values.put("recipeId", recipeId);
        values.put("step", instruction.getStep());
        values.put("instruction", instruction.getInstruction());
        values.put("time", instruction.getTime());
        values.put("imageUrl", instruction.getImageUrl());
        db.insert("Instruction",null, values);
    }

    public void addTag(SQLiteDatabase db, Tag tag, long recipeId){
        String tagName = tag.getName();
        String query = "SELECT tagId FROM Tag WHERE name = '" + tagName + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("name", tagName);
            long tagId = db.insert("Tag", null, values);

            ContentValues linkValues = new ContentValues();
            linkValues.put("tagId", tagId);
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeTags", null, linkValues);
        }else{
            cursor.moveToFirst();
            ContentValues linkValues = new ContentValues();
            linkValues.put("tagId", cursor.getInt(0));
            linkValues.put("recipeId", recipeId);
            db.insert("RecipeTags", null, linkValues);
        }
        cursor.close();
    }

    public void addRecipe(SQLiteDatabase db, Recipe recipe){
        ContentValues values = new ContentValues();
        values.put("userId", recipe.getUser().getId());
        values.put("title", recipe.getTitle());
        values.put("description", recipe.getDescription());
        values.put("imageUrl", recipe.getImageUrl());
        values.put("dateAdded", recipe.getDateAdded());
        values.put("likes", recipe.getLikes());
        values.put("difficulty", recipe.getDifficulty());
        values.put("time", recipe.getTime());
        values.put("portions", recipe.getPortions());
        long entryId = db.insert("Recipe",null, values);
        recipe.setId(entryId);
        finaliseRecipe(db, entryId, recipe);
    }

    //Update the Equipment/Ingredient/Tag/Instruction tables so that they correspond to the recipe.
    public void finaliseRecipe(SQLiteDatabase db, long recipeId, Recipe recipe){
        for (Equipment equipment : recipe.getEquipment()){
            addEquipment(db, equipment, recipeId);
        }
        for (Ingredient ingredient : recipe.getIngredients()){
            addIngredient(db, ingredient, recipeId);
        }
        for (Instruction instruction : recipe.getInstructions()){
            addInstruction(db, instruction, recipeId);
        }
        for (Tag tag : recipe.getTags()){
            addTag(db, tag, recipeId);
        }
    }

    public void addLikedRecipe(SQLiteDatabase db, User user, Recipe recipe){
        ContentValues values = new ContentValues();
        values.put("userId", user.getId());
        values.put("recipeId", recipe.getId());
        db.insert("LikedRecipe",null, values);

        //Possibly want another method for this, or a method to get the number of liked recipes from the DB
        String query = "UPDATE Recipe SET likes = likes + 1 WHERE recipeId = " + recipe.getId();
        db.execSQL(query);
    }

    public void addFavouriteRecipe(SQLiteDatabase db, User user, Recipe recipe){
        ContentValues values = new ContentValues();
        values.put("userId", user.getId());
        values.put("recipeId", recipe.getId());
        db.insert("FavouriteRecipe",null, values);
    }

    //checks user login details
    public boolean findUser(SQLiteDatabase db, String emailInput, String passwordInput){
        String Query = "SELECT * FROM User WHERE email = '" + emailInput + "'" + "AND password = '" + passwordInput +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public String getRecipeTitle(SQLiteDatabase db, int recipeId){
        String Query = "SELECT title FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getRecipeDescription(SQLiteDatabase db, int recipeId){
        String Query = "SELECT description FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getRecipeImageUrl(SQLiteDatabase db, int recipeId){
        String Query = "SELECT imageUrl FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getRecipeDateAdded(SQLiteDatabase db, int recipeId){
        String Query = "SELECT dateAdded FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public int getRecipeLikes(SQLiteDatabase db, int recipeId){
        String Query = "SELECT likes FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        int title = cursor.getInt(0);
        cursor.close();
        return title;
    }

    public int getRecipeDifficulty(SQLiteDatabase db, int recipeId){
        String Query = "SELECT difficulty FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        int title = cursor.getInt(0);
        cursor.close();
        return title;
    }

    public int getRecipeTime(SQLiteDatabase db, int recipeId){
        String Query = "SELECT time FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        int title = cursor.getInt(0);
        cursor.close();
        return title;
    }

    public int getRecipePortions(SQLiteDatabase db, int recipeId){
        String Query = "SELECT portions FROM Recipe WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        int title = cursor.getInt(0);
        cursor.close();
        return title;
    }

    public String getNameOfUser(SQLiteDatabase db, int userId){
        String Query = "SELECT name FROM User WHERE userId ='" + userId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getEmail(SQLiteDatabase db, int userId){
        String Query = "SELECT email FROM User WHERE userId ='" + userId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getUsername(SQLiteDatabase db, int userId){
        String Query = "SELECT username FROM User WHERE userId ='" + userId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public String getUserPassword(SQLiteDatabase db, int userId){
        String Query = "SELECT password FROM User WHERE userId ='" + userId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }

    public ArrayList<String> getRecipeTags(SQLiteDatabase db, int recipeId){
        ArrayList<String> recipeTags = new ArrayList<>();
        String Query = "SELECT tagId FROM RecipeTags WHERE recipeId ='" + recipeId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        for(int i=0;i<cursor.getCount();i++){
            recipeTags.add(cursor.getString(i));
        }
        cursor.close();
        return recipeTags;
    }

    public ArrayList<String> getListOfTagNames(SQLiteDatabase db, ArrayList<String> tagIds){
        ArrayList<String> tagNames = new ArrayList<>();
        for(int i=0;i<tagIds.size();i++){
            String Query = "SELECT name FROM Tag WHERE tagId ='" + tagIds.get(i) + "'";
            Cursor cursor = db.rawQuery(Query, null);
            tagNames.add(cursor.getString(0));
            cursor.close();
        }
        return tagNames;
    }

    public String getTag(SQLiteDatabase db, int tagId){
        String Query = "SELECT name FROM Tag WHERE tagId ='" + tagId + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String title = cursor.getString(0);
        cursor.close();
        return title;
    }
}
