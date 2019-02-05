package com.przemek.recipe.converters;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommandObjectConverter implements Converter<Ingredient, IngredientCommandObject> {

    private final UnitOfMeasureToUnitOfMeasureCommandObjectConverter converter;

    public IngredientToIngredientCommandObjectConverter(UnitOfMeasureToUnitOfMeasureCommandObjectConverter converter) {
        this.converter = converter;
    }

    @Override
    public IngredientCommandObject convert(Ingredient ingredient) {
        IngredientCommandObject ingredientCommand = new IngredientCommandObject();
        ingredientCommand.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUnitOfMeasure(converter.convert(ingredient.getUnitOfMeasure()));
        return ingredientCommand;
    }
}
