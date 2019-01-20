package com.przemek.recipe.bootsrap;

import com.przemek.recipe.domain.*;
import com.przemek.recipe.repositories.CategoryRepository;
import com.przemek.recipe.repositories.RecipeRepository;
import com.przemek.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AppBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    UnitOfMeasureRepository unitOfMeasureRepository;
    RecipeRepository recipeRepository;
    CategoryRepository categoryRepository;

    public AppBootstrap(UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        recipeRepository.saveAll(getAllRecipes());
//        log.debug("creating recipes");
    }

    @Transactional
    private List<Recipe> getAllRecipes() {

        Optional<UnitOfMeasure> teaspoonOpt = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> tablespoonOpt = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> cupOpt = unitOfMeasureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> pinchOpt = unitOfMeasureRepository.findByDescription("Pinch");
        Optional<UnitOfMeasure> ounceOpt = unitOfMeasureRepository.findByDescription("Ounce");
        Optional<UnitOfMeasure> eachOpt = unitOfMeasureRepository.findByDescription("Each");
        Optional<UnitOfMeasure> dashOpt = unitOfMeasureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> pintOpt = unitOfMeasureRepository.findByDescription("Pint");
        Optional<UnitOfMeasure> poundOpt = unitOfMeasureRepository.findByDescription("Pound");

        UnitOfMeasure teaspoon = teaspoonOpt.get();
        UnitOfMeasure tablespoon = tablespoonOpt.get();
        UnitOfMeasure cup = cupOpt.get();
        UnitOfMeasure pinch = pinchOpt.get();
        UnitOfMeasure ounce = ounceOpt.get();
        UnitOfMeasure each = eachOpt.get();
        UnitOfMeasure dash = dashOpt.get();
        UnitOfMeasure pint = pintOpt.get();
        UnitOfMeasure pound = poundOpt.get();

        Optional<Category> americanCategoryOpt = categoryRepository.findByDescription("American");
        Optional<Category> italianCategoryOpt = categoryRepository.findByDescription("Italian");
        Optional<Category> mexicanCategoryOpt = categoryRepository.findByDescription("Mexican");
        Optional<Category> fastFoodCategoryOpt = categoryRepository.findByDescription("Fast Food");

        Category americanCategory = americanCategoryOpt.get();
        Category mexicanCategory = mexicanCategoryOpt.get();
        Category fastFoodCategory = fastFoodCategoryOpt.get();

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\n" +
                "\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!");

        guacamoleNotes.setRecipe(guacamoleRecipe);
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.getIngredients().add(new Ingredient("ripe avocados", BigDecimal.valueOf(2), each));
        guacamoleRecipe.getIngredients().add(new Ingredient("Kosher salt", BigDecimal.valueOf(0.5), teaspoon));
        guacamoleRecipe.getIngredients().add(new Ingredient("fresh lime juice", BigDecimal.valueOf(1), tablespoon));
        guacamoleRecipe.getIngredients().add(new Ingredient("minced red onion", BigDecimal.valueOf(2), cup));
        guacamoleRecipe.getIngredients().add(new Ingredient("serrano chilies", BigDecimal.valueOf(2), each));
        guacamoleRecipe.getIngredients().add(new Ingredient("cilantro", BigDecimal.valueOf(2), tablespoon));
        guacamoleRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", BigDecimal.valueOf(2), dash));
        guacamoleRecipe.getIngredients().add(new Ingredient("ripe tomato", BigDecimal.valueOf(0.5), each));

        guacamoleRecipe.getCategories().add(mexicanCategory);
        guacamoleRecipe.getCategories().add(americanCategory);


        Recipe spicyChickenNuggetsRecipe = new Recipe();
        spicyChickenNuggetsRecipe.setDescription("Spicy Chicken Nuggets");
        spicyChickenNuggetsRecipe.setPrepTime(35);
        spicyChickenNuggetsRecipe.setCookTime(30);
        spicyChickenNuggetsRecipe.setDifficulty(Difficulty.MEDIUM);
        spicyChickenNuggetsRecipe.setDirections("1 Make the marinade: Whisk together the lime juice, rum, soy sauce, and sugar in a bowl until sugar has dissolved.\n" +
                "\n" +
                "2 Marinate the chicken: Add the chicken to the marinade and let marinate for 30 minutes at room temperature (can marinate longer chilled, but allow to come to room temp for 30 minutes before cooking).\n" +
                "\n" +
                "3 Heat the oil for frying: Pour enough oil in a skillet so that it comes up the sides at least a half an inch. Heat oil on medium high until it is shimmering, and a little pinch of flour sizzles when you drop it in the pan.\n" +
                "\n" +
                "4 Dredge the chicken: While the oil is heating, whisk together the flour, paprika, and salt in a bowl. Remove the chicken pieces from the marinade and pat dry with paper towels. Dredge the chicken pieces in the flour mixture and move to a plate.\n" +
                "\n" +
                "5 Fry the chicken in batches (about 3), about 3 minutes on each side, until deep golden brown and the chicken is cooked through. (If the chicken is browning too quickly, reduce the heat a bit).\n" +
                "\n" +
                "Remove to a plate lined with paper towels to soak up the excess oil.");

        Notes spicyChickenNuggetsRecipeNotes = new Notes();
        spicyChickenNuggetsRecipeNotes.setRecipeNotes("use good quality dark bear");

//        spicyChickenNuggetsRecipeNotes.setRecipe(spicyChickenNuggetsRecipe);
        spicyChickenNuggetsRecipe.setNotes(spicyChickenNuggetsRecipeNotes);

        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("dark rum", BigDecimal.valueOf(0.25), cup));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("lime juice", BigDecimal.valueOf(0.25), cup));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("soy sauce", BigDecimal.valueOf(0.25), cup));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("sugar", BigDecimal.valueOf(1), tablespoon));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("boneless chicken thighs or breasts", BigDecimal.valueOf(1.5), pound));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("vegetable oil (canola oil, rice bran oil, peanut oil or other high smoke point oil)", BigDecimal.valueOf(1), tablespoon));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("flour", BigDecimal.valueOf(0.5), cup));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.5), teaspoon));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("paprika", BigDecimal.valueOf(0.5), teaspoon));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("lime wedges", BigDecimal.valueOf(1), each));
        spicyChickenNuggetsRecipe.addIngredient(new Ingredient("hot sauce", BigDecimal.valueOf(200), each));

        spicyChickenNuggetsRecipe.getCategories().add(americanCategory);
        spicyChickenNuggetsRecipe.getCategories().add(fastFoodCategory);


        List<Recipe> recipes = new ArrayList<>();
        recipes.add(guacamoleRecipe);
        recipes.add(spicyChickenNuggetsRecipe);

        return recipes;
    }

}
