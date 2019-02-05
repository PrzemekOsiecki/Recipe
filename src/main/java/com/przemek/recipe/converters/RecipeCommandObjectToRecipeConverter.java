package com.przemek.recipe.converters;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandObjectToRecipeConverter implements Converter<RecipeCommandObject, Recipe> {

    private final CategoryCommandObjectToCategoryConverter categoryConverter;
    private final IngredientCommandObjectToIngredientConverter ingredientConverter;
    private final NotesCommandObjectToNotesConverter notesConverter;

    public RecipeCommandObjectToRecipeConverter(CategoryCommandObjectToCategoryConverter categoryConverter,
                                                IngredientCommandObjectToIngredientConverter ingredientConverter,
                                                NotesCommandObjectToNotesConverter notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommandObject recipeCommandObject) {
        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommandObject.getId());
        recipe.setCookTime(recipeCommandObject.getCookTime());
        recipe.setPrepTime(recipeCommandObject.getPrepTime());
        recipe.setDescription(recipeCommandObject.getDescription());
        recipe.setDifficulty(recipeCommandObject.getDifficulty());
        recipe.setDirections(recipeCommandObject.getDirections());
        recipe.setServings(recipeCommandObject.getServings());
//        recipe.setSource(recipeCommandObject.getSource());
        recipe.setUrl(recipeCommandObject.getUrl());
        recipe.setNotes(notesConverter.convert(recipeCommandObject.getNotes()));

        if (recipeCommandObject.getCategories() != null && recipeCommandObject.getCategories().size() > 0){
            recipeCommandObject.getCategories()
                    .forEach( category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipeCommandObject.getIngredients() != null && recipeCommandObject.getIngredients().size() > 0){
            recipeCommandObject.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
