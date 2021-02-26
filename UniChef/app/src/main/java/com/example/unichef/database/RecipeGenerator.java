package com.example.unichef.database;

import java.util.ArrayList;

public class RecipeGenerator {
    public void  generateRecipes(DBHelper db, User user){
        //Example recipe
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Spoon"));
        equipment.add(new Equipment("Fork"));
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Vegetable"));
        ingredients.add(new Ingredient("Fish"));
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Fry", 20, "img"));
        instructions.add(new Instruction(2, "Eat", 10, "help me"));
        Recipe recipe = new Recipe(user, "Title", "Description", "imageurl", "date", instructions, ingredients, equipment, tags, 10, 5, 20, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);


    }
}
