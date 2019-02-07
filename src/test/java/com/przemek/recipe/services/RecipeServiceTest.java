package com.przemek.recipe.services;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.converters.RecipeCommandObjectToRecipeConverter;
import com.przemek.recipe.converters.RecipeToRecipeCommandObjectConverter;
import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    RecipeService uut;

    @Mock
    RecipeRepository recipeRepositoryMock;

    @Mock
    RecipeToRecipeCommandObjectConverter recipeToRecipeCommandObjectConverter;

    @Mock
    RecipeCommandObjectToRecipeConverter recipeCommandObjectToRecipeConverter;

    Recipe recipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        uut = new RecipeService(recipeRepositoryMock, recipeCommandObjectToRecipeConverter,
                recipeToRecipeCommandObjectConverter);

        recipe = new Recipe();
        recipe.setId(1L);
    }

    @Test
    public void shouldReturnAllExistingRecipes() {
        //given
        Set<Recipe> expectedRecipes = new HashSet<>();
        expectedRecipes.add(new Recipe());

        //when
        when(recipeRepositoryMock.findAll()).thenReturn(expectedRecipes);

        Set<Recipe> actualRecipes = uut.getAllRecipes();

        //then
        assertEquals(expectedRecipes, actualRecipes);
        assertEquals(actualRecipes.size(), 1);
        verify(recipeRepositoryMock, times(1)).findAll();
    }

    @Test
    public void shouldReturnRecipeById() {
        //when
        when(recipeRepositoryMock.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe actualRecipe = uut.findRecipeById(1L);

        //then
        assertNotNull(actualRecipe);
        assertEquals(recipe, actualRecipe);
        verify(recipeRepositoryMock, times(1)).findById(1L);
    }

    @Test
    public void shouldSaveRecipe() {
        RecipeCommandObject recipeCommandObject = new RecipeCommandObject();
        recipeCommandObject.setId(1L);

        when(recipeRepositoryMock.save(recipe)).thenReturn(recipe);
        when(recipeCommandObjectToRecipeConverter.convert(recipeCommandObject)).thenReturn(recipe);
        when(recipeToRecipeCommandObjectConverter.convert(any())).thenReturn(recipeCommandObject);

        RecipeCommandObject actualRecipeCommandObject = uut.saveRecipe(recipeCommandObject);

        assertNotNull(actualRecipeCommandObject);
        assertEquals(recipeCommandObject, actualRecipeCommandObject);
        assertEquals(recipeCommandObject.getId(), actualRecipeCommandObject.getId());
    }

    @Test
    public void shouldRemoveRecipe() {
        //when
        uut.removeRecipe(1L);
        //then
        verify(recipeRepositoryMock, times(1)).deleteById(1L);
    }
}