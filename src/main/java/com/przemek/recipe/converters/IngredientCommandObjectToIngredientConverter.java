package com.przemek.recipe.converters;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.domain.Ingredient;
import com.przemek.recipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandObjectToIngredientConverter implements Converter<IngredientCommandObject, Ingredient> {

    private final UnitOfMeasureCommandObjectToUnitOfMeasureConverter uomConverter;

    public IngredientCommandObjectToIngredientConverter(UnitOfMeasureCommandObjectToUnitOfMeasureConverter uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public Ingredient convert(IngredientCommandObject ingredientCommandObject) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommandObject.getId());

        if(ingredientCommandObject.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommandObject.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        ingredient.setAmount(ingredientCommandObject.getAmount());
        ingredient.setDescription(ingredientCommandObject.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(ingredientCommandObject.getUnitOfMeasure()));
        return ingredient;
    }
}
