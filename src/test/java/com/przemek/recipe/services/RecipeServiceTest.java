package com.przemek.recipe.services;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    RecipeService uut;

    @Mock
    RecipeRepository recipeRepositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        uut = new RecipeService(recipeRepositoryMock);
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
}