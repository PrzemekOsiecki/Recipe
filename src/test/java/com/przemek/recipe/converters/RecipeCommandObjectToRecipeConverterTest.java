package com.przemek.recipe.converters;

import com.przemek.recipe.commands.CategoryCommandObject;
import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.commands.NotesCommandObject;
import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.domain.Difficulty;
import com.przemek.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class RecipeCommandObjectToRecipeConverterTest {

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("3");
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;

    RecipeCommandObjectToRecipeConverter uut;


    @Before
    public void setUp() throws Exception {
        uut = new RecipeCommandObjectToRecipeConverter(new CategoryCommandObjectToCategoryConverter(),
                new IngredientCommandObjectToIngredientConverter(new UnitOfMeasureCommandObjectToUnitOfMeasureConverter()),
                new NotesCommandObjectToNotesConverter());
    }

    @Test
    public void convert() throws Exception {
        //given
        RecipeCommandObject recipeCommand = new RecipeCommandObject();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
//        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommandObject notes = new NotesCommandObject();
        notes.setId(NOTES_ID);

        recipeCommand.setNotes(notes);

        CategoryCommandObject category = new CategoryCommandObject();
        category.setId(CAT_ID_1);

        CategoryCommandObject category2 = new CategoryCommandObject();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommandObject ingredient = new IngredientCommandObject();
        ingredient.setId(INGRED_ID_1);

        IngredientCommandObject ingredient2 = new IngredientCommandObject();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        Recipe recipe  = uut.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
//        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(java.util.Optional.ofNullable(NOTES_ID), recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}