package com.example.unichef.database;

import java.util.ArrayList;
import java.util.Date;

public class RecipeGenerator {
    public void generateRecipes(DBHelper db, User user) {
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
        Recipe recipe = new Recipe(user, "Title", "Description", "imageurl", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 5, 20, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);

    }

    public void getCarbonaraRecipe(DBHelper db, User user) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Saucepan"));
        equipment.add(new Equipment("Cooking knife"));
        equipment.add(new Equipment("Fork"));
        equipment.add(new Equipment("Frying pan or Wok"));
        equipment.add(new Equipment("Tongs"));
        equipment.add(new Equipment("Grater"));
        equipment.add(new Equipment("Bowl"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("100g pancetta"));
        ingredients.add(new Ingredient("100g parmesan cheese"));
        ingredients.add(new Ingredient("3 large eggs"));
        ingredients.add(new Ingredient("2 garlic cloves, peeled and whole"));
        ingredients.add(new Ingredient("350g spaghetti"));
        ingredients.add(new Ingredient("50g unsalted butter"));
        ingredients.add(new Ingredient("Salt and pepper for seasoning"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));
        tags.add(new Tag("Pasta"));
        tags.add(new Tag("Food"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Put a large saucepan of water on to boil", 20, "img"));
        instructions.add(new Instruction(2, "Chop the pancetta and finely grate the parmesan", 10, "help me"));
        instructions.add(new Instruction(3, "Beat the eggs in a bowl with the fork and season with black pepper. Add the grated parmesan and mix well.", 10, "help me"));
        instructions.add(new Instruction(4, "Add 1 tsp salt to the boiling water, add spaghetti and when the water comes back to the boil, cook at a constant simmer, covered, for 10 minutes", 10, "help me"));
        instructions.add(new Instruction(5, "Squash the garlic cloves with the side of your knife.", 10, "help me"));
        instructions.add(new Instruction(6, "While the spaghetti is cooking, add the butter to the large frying pan on a medium/high temperature, and add the pancetta and garlic once the butter has melted. When pancetta is brown, remove garlic from the pan and discard.", 10, "help me"));
        instructions.add(new Instruction(7, "Keep the pancetta on a low heat until the pasta is done cooking, and use tongs to move the pasta to the frying pan and continue to cook for 2 minutes.", 10, "help me"));
        instructions.add(new Instruction(8, "Take the frying pan off the heat, leave to stand for 30 seconds, and add the egg-cheese mixture. Mix everything together, and the heat of the pasta will cook the egg (don't scramble it!). Add a couple of spoons of the pasta water to the frying pan.", 10, "help me"));
        instructions.add(new Instruction(9, "Serve and enjoy!", 10, "help me"));

        Recipe recipe = new Recipe(user, "Spaghetti Carbonara", "This cheesy pasta dish is an Italian favourite and with the right technique, you can make it perfect every time", "carbonara.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 5, 20, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void getBurgerRecipe(DBHelper db, User user) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Oven or Grill Tray"));
        equipment.add(new Equipment("Bowl"));
        equipment.add(new Equipment("Plate"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("500g Beef Mince"));
        ingredients.add(new Ingredient("1 Red Onion"));
        ingredients.add(new Ingredient("2 Garlic Cloves"));
        ingredients.add(new Ingredient("1 Teaspoon Cayenne Pepper"));
        ingredients.add(new Ingredient("1 Egg"));
        ingredients.add(new Ingredient("Salt and Pepper for Seasoning"));
        ingredients.add(new Ingredient("Burger Buns"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));
        tags.add(new Tag("Finger food"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Preheat grill on a Medium-High setting.", 20, "img"));
        instructions.add(new Instruction(2, "Combine all the burger ingredients in a bowl until they are really well mixed together.", 10, "help me"));
        instructions.add(new Instruction(3, "Shape the mixture into 4 patties", 10, "help me"));
        instructions.add(new Instruction(4, "Grill for 4-5 minutes on each side, depending on how well cooked you like them.", 10, "help me"));
        instructions.add(new Instruction(5, "Serve in burger buns", 20, "img"));

        Recipe recipe = new Recipe(user, "Beef Burger Recipe", "Easy and Quick to make", "burgers.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 1, 30, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void getFajitaRecipe(DBHelper db, User user) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Cooking Knife"));
        equipment.add(new Equipment("Griddle pan or wok"));
        equipment.add(new Equipment("Large bowl"));
        equipment.add(new Equipment("Tongs"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("2 large chicken breasts, finely sliced"));
        ingredients.add(new Ingredient("1 red onion, finely sliced"));
        ingredients.add(new Ingredient("1 red pepper, sliced"));
        ingredients.add(new Ingredient("1 red chilli, finely sliced (optional)"));
        ingredients.add(new Ingredient("1 heaped tbsp smoked paprika"));
        ingredients.add(new Ingredient("1 tbsp ground coriander"));
        ingredients.add(new Ingredient("pinch of ground cumin"));
        ingredients.add(new Ingredient("2 medium garlic cloves, crushed"));
        ingredients.add(new Ingredient("4 tbsp olive oil"));
        ingredients.add(new Ingredient("1 lime, juiced"));
        ingredients.add(new Ingredient("4-5 drops Tabasco"));
        ingredients.add(new Ingredient("Choice of Tex Mex dips"));
        ingredients.add(new Ingredient("6 medium tortillas"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));
        tags.add(new Tag("Food"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Mix spices, garlic, olive oil, juice of the lime and tabasco in a big bowl", 20, "img"));
        instructions.add(new Instruction(2, "Stir the chicken, pepper, onion and chilli into the bowl", 10, "help me"));
        instructions.add(new Instruction(3, "Heat a pan until smoking hot and add the chicken and marinade to the pan", 10, "help me"));
        instructions.add(new Instruction(4, "Keep everything moving over a high heat for about 5 mins using tongs until you get a nice charred effect. If your pan is small you may need to do this in two batches.", 10, "help me"));
        instructions.add(new Instruction(5, "To check the chicken is cooked, find the thickest part and tear in half – if any part is still raw cook until done.", 10, "help me"));
        instructions.add(new Instruction(6, "Serve in tortilla wraps", 10, "help me"));

        Recipe recipe = new Recipe(user, "Chicken Fajitas", "Need a simple, vibrant midweek meal that you'll love? Put together these easy chicken fajitas and people can create their own masterpieces at the table", "fajitas.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 5, 15, 3);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void getSpagBolRecipe(DBHelper db, User user) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Cooking Knife"));
        equipment.add(new Equipment("Large saucepan or wok"));
        equipment.add(new Equipment("Saucepan"));
        equipment.add(new Equipment("Wooden spoon"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1 tbsp olive oil"));
        ingredients.add(new Ingredient("2 medium onions, finely chopped"));
        ingredients.add(new Ingredient("2 carrots, trimmed and finely chopped"));
        ingredients.add(new Ingredient("2 garlic cloves finely chopped"));
        ingredients.add(new Ingredient("500g beef mince"));
        ingredients.add(new Ingredient("2 x 400g tins plum tomatoes"));
        ingredients.add(new Ingredient("1 tsp dried oregano"));
        ingredients.add(new Ingredient("2 tbsp tomato purée"));
        ingredients.add(new Ingredient("1 beef stock cube"));
        ingredients.add(new Ingredient("1 red chilli deseeded and finely chopped (optional)"));
        ingredients.add(new Ingredient("6 cherry tomatoes sliced in half"));
        ingredients.add(new Ingredient("400g spaghetti"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Food"));
        tags.add(new Tag("Pasta"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Put a large saucepan on a medium heat and add 1 tbsp olive oil.", 20, "img"));
        instructions.add(new Instruction(2, "Add the onions, carrots, garlic on a low heat and fry for 10 minutes, stirring often until soft.", 10, "help me"));
        instructions.add(new Instruction(3, "Increase the heat to medium-high, add beef mince and cook stirring for 3-4 mins until the meat is browned all over.", 10, "help me"));
        instructions.add(new Instruction(4, "Add plum tomatoes, dried oregano, tomato purée, beef stock cube, red chilli (if using) and 6 halved cherry tomatoes. Stir with a wooden spoon, breaking up the plum tomatoes.", 10, "help me"));
        instructions.add(new Instruction(5, "Bring to the boil, reduce to a gentle simmer and cover with a lid. Cook for 1 hr 15 mins stirring occasionally, until you have a rich, thick sauce.", 10, "help me"));
        instructions.add(new Instruction(6, "When the bolognese is nearly finished, cook 400g spaghetti following the pack instructions.", 10, "help me"));
        instructions.add(new Instruction(7, "Drain the spaghetti and serve with bolognese on top", 10, "help me"));

        Recipe recipe = new Recipe(user, "Spaghetti Bolognese", "super easy and a true Italian classic with a meaty, chilli sauce", "spagbol.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 5, 110, 3);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void  getCurryRecipe(DBHelper db, User user){
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Chopping Board"));
        equipment.add(new Equipment("Pan"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("600g Chicken Breast (diced)"));
        ingredients.add(new Ingredient("1 Whole Jar of Curry Sauce"));
        ingredients.add(new Ingredient("1/2 Red Onion (finely sliced"));
        ingredients.add(new Ingredient("6 Cherry Tomatoes (quartered)"));
        ingredients.add(new Ingredient("1 tablespoon Vegetable Oil"));
        ingredients.add(new Ingredient("1 Whole Jar of Curry Sauce"));
        ingredients.add(new Ingredient("Juice of 1/2 Lemon (optional)"));
        ingredients.add(new Ingredient("Salt and Sugar to taste"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));
        tags.add(new Tag("Indian"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Heat the vegetable oil in a pan and add the red onions.", 20, "img"));
        instructions.add(new Instruction(2, "After 2 minutes add the chicken and allow to seal for a few minutes", 10, "help me"));
        instructions.add(new Instruction(3, "Add the tomatoes and the Cooking Sauce. Cover and leave to cook through for 15 minutes.", 10, "help me"));
        instructions.add(new Instruction(4, "Stir occasionally to prevent the Sauce from sticking to the bottom of the pan", 10, "help me"));
        instructions.add(new Instruction(5, "Taste and adjust the seasoning with salt, sugar and lemon juice", 20, "img"));

        Recipe recipe = new Recipe(user, "Chicken Curry", "Flavourful and colourful quick chicken curry recipe", "chickencurry.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 1, 30, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void  getPancakeRecipe(DBHelper db, User user){
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Frying pan"));
        equipment.add(new Equipment("Whisk (or fork)"));
        equipment.add(new Equipment("Large bowl or jug"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("100g plain flour"));
        ingredients.add(new Ingredient("2 large eggs"));
        ingredients.add(new Ingredient("300ml milk"));
        ingredients.add(new Ingredient("1 tbsp sunflower or vegetable oil, plus a little extra for frying"));
        ingredients.add(new Ingredient("Toppings!"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Quick"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Put plain flour, eggs, milk, 1 tbsp sunflower or vegetable oil and a pinch of salt into a bowl or large jug, then whisk to a smooth batter.", 20, "img"));
        instructions.add(new Instruction(2, "Set aside for 30 mins to rest if you have time, or start cooking straight away.", 10, "help me"));
        instructions.add(new Instruction(3, "Set a medium frying pan or crêpe pan over a medium heat and carefully wipe it with some oiled kitchen paper.", 10, "help me"));
        instructions.add(new Instruction(4, "When hot, cook your pancakes for 1 min on each side until golden, keeping them warm in a low oven as you go.", 10, "help me"));
        instructions.add(new Instruction(5, "Serve with your favourite toppings!", 20, "img"));

        Recipe recipe = new Recipe(user, "Pancake", "Learn a skill for life with this foolproof crêpe recipe that ensures perfect pancakes every time – elaborate flip optional", "pancakes.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 1, 10-40, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void  getVeganChilliRecipe(DBHelper db, User user){
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Chopping Board"));
        equipment.add(new Equipment("Oven Tray"));
        equipment.add(new Equipment("Large Saucepan"));
        equipment.add(new Equipment("Knife"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("3 tbsp Olive Oil"));
        ingredients.add(new Ingredient("2 Sweet Potatoes (Peeled and cut into medium chunks)"));
        ingredients.add(new Ingredient("2 tbsp Smoked Paprika"));
        ingredients.add(new Ingredient("2 tbsp Ground Cumin"));
        ingredients.add(new Ingredient("1 onion (chopped)"));
        ingredients.add(new Ingredient("2 carrots (chopped)"));
        ingredients.add(new Ingredient("2 celery sticks (chopped)"));
        ingredients.add(new Ingredient("2 garlic cloves (crushed)"));
        ingredients.add(new Ingredient("1-2 tsp Chilli Powder"));
        ingredients.add(new Ingredient("1 tsp Dried Oregano"));
        ingredients.add(new Ingredient("1 tbsp tomato purée"));
        ingredients.add(new Ingredient("1 red pepper (Cut into chunks)"));
        ingredients.add(new Ingredient("2 x 400g cans chopped tomatoes"));
        ingredients.add(new Ingredient("400g can black beans (Drained)"));
        ingredients.add(new Ingredient("400g can kidney beans (Drained)"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Vegan"));
        tags.add(new Tag("Chilli"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Heat the oven to 200C/180C fan/gas 6. Put the sweet potato in a tray and drizzle over 1½ tbsp oil, 1 tsp smoked paprika and 1 tsp ground cumin. Give everything a good mix so that all the chunks are coated in spices, season with salt and pepper, then roast for 25 mins until cooked", 20, "img"));
        instructions.add(new Instruction(2, "Meanwhile, heat the remaining oil in a large saucepan over a medium heat. Add the onion, carrot and celery. Cook for 8-10 mins, stirring occasionally until soft, then crush in the garlic and cook for 1 min more. Add the remaining dried spices and tomato purée. Give everything a good mix and cook for 1 min more", 10, "help me"));
        instructions.add(new Instruction(3, "Add the red pepper, chopped tomatoes and 200ml water. Bring the chilli to a boil, then simmer for 20 mins. Tip in the beans and cook for another 10 mins before adding the sweet potato.", 10, "help me"));

        Recipe recipe = new Recipe(user, "Vegan Chilli", "Flavour packed VEGAN chilli that can be enjoyed with rice.", "veganchilli.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 1, 45, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }

    public void  getLasagneRecipe(DBHelper db, User user){
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment("Frying Pan"));
        equipment.add(new Equipment("Baking Dish"));

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("500g lean minced beef"));
        ingredients.add(new Ingredient("6 lasagne sheets"));
        ingredients.add(new Ingredient("75g grated cheese"));
        ingredients.add(new Ingredient("500g Creamy Lasagne Sauce "));
        ingredients.add(new Ingredient("500g Tomato Lasagne Sauce"));

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Easy"));
        tags.add(new Tag("Italian"));
        tags.add(new Tag("Pasta"));

        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(1, "Pre-heat the oven to 200°C / fan oven 180°C / Gas 5. Heat a large frying pan and add the mince, cook over a high heat, stirring well until browned and cooked through. Tip in the Tomato Sauce, stir and simmer for 5 minutes, then remove from the heat", 20, "img"));
        instructions.add(new Instruction(2, "Tip half the Bolognese mixture into a large rectangular baking dish measuring about 26cm x 20cm. Arrange 3 lasagne sheets on top, then spread half the Creamy Sauce over them. Repeat the layers, then sprinkle the grated cheese evenly over the surface", 10, "help me"));
        instructions.add(new Instruction(3, "Bake for around 30 minutes, until cooked and golden brown. Allow to stand for a few minutes, then serve.", 10, "help me"));

        Recipe recipe = new Recipe(user, "Easy Lasagne", "Saucy Lasagne with Sauces so takes less than half the time than normal!", "lasagna.png", new Date().getTime(), instructions, ingredients, equipment, tags, 10, 1, 50, 4);
        db.addRecipe(db.getWritableDatabase(), recipe);
    }


}








