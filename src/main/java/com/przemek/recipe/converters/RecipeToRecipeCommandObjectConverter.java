package com.przemek.recipe.converters;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.domain.Category;
import com.przemek.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommandObjectConverter implements Converter<Recipe, RecipeCommandObject> {

    private final CategoryToCategoryCommandObjectConverter categoryConverter;
    private final IngredientToIngredientCommandObjectConverter ingredientConverter;
    private final NotesToNotesCommandObjectConverter notesConverter;

    public RecipeToRecipeCommandObjectConverter(CategoryToCategoryCommandObjectConverter categoryConverter,
                                 IngredientToIngredientCommandObjectConverter ingredientConverter,
                                 NotesToNotesCommandObjectConverter notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommandObject convert(Recipe recipe) {
        final RecipeCommandObject command = new RecipeCommandObject();
        command.setId(recipe.getId());
        command.setCookTime(recipe.getCookTime());
        command.setPrepTime(recipe.getPrepTime());
        command.setDescription(recipe.getDescription());
        command.setDifficulty(recipe.getDifficulty());
        command.setDirections(recipe.getDirections());
        command.setServings(recipe.getServings());
//        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setImage(recipe.getImage());
        command.setNotes(notesConverter.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
