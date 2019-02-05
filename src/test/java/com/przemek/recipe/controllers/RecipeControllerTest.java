package com.przemek.recipe.controllers;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    RecipeController uut;

    @Mock
    RecipeService recipeServiceMock;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uut = new RecipeController(recipeServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }

    @Test
    public void shouldReturnRecipeById() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeServiceMock.findRecipeById(1L)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe"));
    }
}