package com.example.unichef.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, "database.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //comment table
        db.execSQL("CREATE TABLE Comment (commentId INTEGER PRIMARY KEY, userId INTEGER, recipeId INTEGER, comment TEXT, date TEXT, FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //equipment table
        db.execSQL("CREATE TABLE Equipment (equipmentId INTEGER PRIMARY KEY, name, TEXT)");
        //favourite recipe table
        db.execSQL("CREATE TABLE FavouriteRecipe (userId INTEGER, recipeId Integer, PRIMARY KEY(userId, recipeId), FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //ingredient table
        db.execSQL("CREATE TABLE Ingredient(ingredientId INTEGER PRIMARY KEY, name TEXT)");
        //Instruction table
        db.execSQL("CREATE TABLE Instruction(instructionId INTEGER PRIMARY KEY, recipeId INTEGER, step INTEGER, instruction TEXT, time INT, imageUrl TEXT, FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Liked recipe table
        db.execSQL("CREATE TABLE LikedRecipe(userId INTEGER, recipeId INTEGER, PRIMARY KEY(userId, recipeId), FOREIGN KEY(userId) REFERENCES User(userId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe table
        db.execSQL("CREATE TABLE Recipe(recipeId INTEGER PRIMARY KEY, userId INTEGER, title TEXT, description TEXT, imageUrl TEXT, dateAdded TEXT, likes INTEGER, difficulty INTEGER, time INTEGER, portions INTEGER, FOREIGN KEY(userId) REFERENCES User(userId))");
        //Recipe equipment table
        db.execSQL("CREATE TABLE RecipeEquipment(equipmentId INTEGER, recipeId INTEGER, PRIMARY KEY(equipmentId, recipeId), FOREIGN KEY(equipmentId) REFERENCES Equipment(equipmentId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe ingredients table
        db.execSQL("CREATE TABLE RecipeIngredients(ingredientsId INTEGER, recipeId INTEGER, quantity INTEGER, PRIMARY KEY(ingredientsId, recipeId), FOREIGN KEY(ingredientsId) REFERENCES Ingredient(ingredientsId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Recipe tags table
        db.execSQL("CREATE TABLE RecipeTags(tagId INTEGER, recipeId INTEGER, PRIMARY KEY(tagId, recipeId), FOREIGN KEY(tagId) REFERENCES Tag(tagId), FOREIGN KEY(recipeId) REFERENCES Recipe(recipeId))");
        //Tags table
        db.execSQL("CREATE TABLE Tag(tagId INTEGER PRIMARY KEY, name TEXT)");
        //User table
        db.execSQL("CREATE TABLE User(userId INTEGER PRIMARY KEY, name TEXT, email TEXT, username TEXT,  password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addUser(SQLiteDatabase db, User user){
        ContentValues values = new ContentValues();
        values.put("userId", user.getUserId());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        db.insert("User",null, values);
    }

    public void addComment(SQLiteDatabase db, Comment comment){
        ContentValues values = new ContentValues();
        values.put("commentId", comment.getCommentId());
        values.put("userId", comment.getUserId());
        values.put("recipeId", comment.getRecipeId());
        values.put("comment", comment.getComment());
        values.put("date", comment.getDate());
        db.insert("Comment",null, values);
    }

    public void addInstruction(SQLiteDatabase db, Instruction instruction){
        ContentValues values = new ContentValues();
        values.put("instructionId", instruction.getInstructionId());
        values.put("recipeId", instruction.getRecipeId());
        values.put("step", instruction.getStep());
        values.put("instruction", instruction.getInstruction());
        values.put("time", instruction.getTime());
        values.put("imageUrl", instruction.getImageUrl());
        db.insert("Instruction",null, values);
    }

    public void addRecipeEquipment(SQLiteDatabase db, RecipeEquipment recipeEquipment){
        ContentValues values = new ContentValues();
        values.put("equipmentId", recipeEquipment.getEquipmentId());
        values.put("recipeId", recipeEquipment.getRecipeId());
        db.insert("RecipeEquipment",null, values);
    }

    public void addEquipment(SQLiteDatabase db, Equipment equipment){
        ContentValues values = new ContentValues();
        values.put("equipmentId", equipment.getEquipmentId());
        values.put("name", equipment.getName());
        db.insert("Equipment",null, values);
    }

    public void addIngredient(SQLiteDatabase db, Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put("ingredientId", ingredient.getIngredientId());
        values.put("name", ingredient.getName());
        db.insert("Instruction",null, values);
    }

    public void addTag(SQLiteDatabase db, Tag tag){
        ContentValues values = new ContentValues();
        values.put("tagId", tag.getTagId());
        values.put("name", tag.getName());
        db.insert("Tag",null, values);
    }

    public void addRecipeEquipment(SQLiteDatabase db, RecipeIngredients recipeIngredients){
        ContentValues values = new ContentValues();
        values.put("ingredientId", recipeIngredients.getIngredientsId());
        values.put("recipeId", recipeIngredients.getRecipeId());
        values.put("quantity", recipeIngredients.getQuantity());
        db.insert("RecipeEquipment",null, values);
    }

    public void addRecipeTags(SQLiteDatabase db, RecipeTags recipeTags){
        ContentValues values = new ContentValues();
        values.put("tagId", recipeTags.getTagId());
        values.put("recipeId", recipeTags.getRecipeId());
        db.insert("RecipeTags",null, values);
    }

    public void addRecipe(SQLiteDatabase db, Recipe recipe){
        ContentValues values = new ContentValues();
        values.put("recipeId", recipe.getRecipeId());
        values.put("userId", recipe.getUserId());
        values.put("title", recipe.getTitle());
        values.put("description", recipe.getDescription());
        values.put("imageUrl", recipe.getImageUrl());
        values.put("dateAdded", recipe.getDateAdded());
        values.put("likes", recipe.getLikes());
        values.put("difficulty", recipe.getDifficulty());
        values.put("time", recipe.getTime());
        values.put("portions", recipe.getPortions());
        db.insert("Recipe",null, values);
    }

    public void addLikedRecipe(SQLiteDatabase db, LikedRecipe likedRecipe){
        ContentValues values = new ContentValues();
        values.put("userId", likedRecipe.getUserId());
        values.put("recipeId", likedRecipe.getRecipeId());
        db.insert("LikedRecipe",null, values);
    }

    public void addFavouriteRecipe(SQLiteDatabase db, FavouriteRecipe favouriteRecipe){
        ContentValues values = new ContentValues();
        values.put("userId", favouriteRecipe.getUserId());
        values.put("recipeId", favouriteRecipe.getRecipeId());
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

    public int generateUserId(SQLiteDatabase db){
        String Query = "SELECT MAX(userId) FROM User";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return 1;
        }
        int max = cursor.getInt(0);
        return max+1;
    }

    public int generateRecipeId(SQLiteDatabase db){
        String Query = "SELECT MAX(recipeId) FROM Recipe";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() < 1){
            cursor.close();
            return 1;
        }
        int max = cursor.getInt(0);
        cursor.close();
        return max+1;
    }

    public int generateEquipmentId(SQLiteDatabase db){
        String Query = "SELECT MAX(equipmentId) FROM Equipment";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() < 1){
            return 1;
        }
        int max = cursor.getInt(0);
        return max+1;
    }

    public int generateTagId(SQLiteDatabase db){
        String Query = "SELECT MAX(tagId) FROM Tag";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() < 1){
            return 1;
        }
        int max = cursor.getInt(0);
        return max+1;
    }

    public int generateCommentId(SQLiteDatabase db){
        String Query = "SELECT MAX(commentId) FROM Comment";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() < 1){
            return 1;
        }
        int max = cursor.getInt(0);
        return max+1;
    }
    
}
