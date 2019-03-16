package com.przemek.recipe.services;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.converters.IngredientCommandObjectToIngredientConverter;
import com.przemek.recipe.converters.IngredientToIngredientCommandObjectConverter;
import com.przemek.recipe.domain.Ingredient;
import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import com.przemek.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class IngredientServiceTest {

    IngredientService uut;

    @Mock
    private IngredientToIngredientCommandObjectConverter ingredientToIngredientCommandMock;

    @Mock
    private IngredientCommandObjectToIngredientConverter ingredientCommandToIngredientMock;

    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepositoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        uut = new IngredientService(ingredientToIngredientCommandMock, ingredientCommandToIngredientMock,
                recipeRepositoryMock, unitOfMeasureRepositoryMock);
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

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(recipeOptional);
        when(ingredientToIngredientCommandMock.convert(ingredient3)).thenReturn(ingredientCommandObject);

        //then
        IngredientCommandObject ingredientCommand = uut.findIngredientByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void shouldDeleteIngredient() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(recipeOptional);

        //when
        uut.deleteIngredient(1L, 3L);

        //then
        verify(recipeRepositoryMock, times(1)).findById(anyLong());
        verify(recipeRepositoryMock, times(1)).save(any(Recipe.class));
    }

}