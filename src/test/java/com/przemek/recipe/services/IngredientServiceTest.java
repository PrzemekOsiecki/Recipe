package com.przemek.recipe.services;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.converters.IngredientToIngredientCommandObjectConverter;
import com.przemek.recipe.converters.UnitOfMeasureToUnitOfMeasureCommandObjectConverter;
import com.przemek.recipe.domain.Ingredient;
import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class IngredientServiceTest {

    @Mock
    private IngredientToIngredientCommandObjectConverter ingredientToIngredientCommand;

    @Mock
    private RecipeRepository recipeRepository;

    IngredientService uut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        uut = new IngredientService(ingredientToIngredientCommand, recipeRepository);
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        IngredientCommandObject ingredientCommandObject = new IngredientCommandObject();
        ingredientCommandObject.setId(3L);
        ingredientCommandObject.setRecipeId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(ingredientToIngredientCommand.convert(ingredient3)).thenReturn(ingredientCommandObject);

        //then
        IngredientCommandObject ingredientCommand = uut.findIngredientByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

}