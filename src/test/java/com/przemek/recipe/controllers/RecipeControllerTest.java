package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.exceptions.NotFoundException;
import com.przemek.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    RecipeController uut;

    @Mock
    RecipeService recipeServiceMock;

    MockMvc mockMvc;

    Recipe recipe;

    @Before
    public void setUp() throws Exception {
        recipe = new Recipe();
        recipe.setId(1L);

        MockitoAnnotations.initMocks(this);
        uut = new RecipeController(recipeServiceMock);

        mockMvc = MockMvcBuilders.standaloneSetup(uut)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }

    @Test
    public void shouldReturnRecipeById() throws Exception {
        when(recipeServiceMock.findRecipeById(1L)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe"));
    }

    @Test
    public void shouldReturn404Error() throws Exception {
        when(recipeServiceMock.findRecipeById(anyLong())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void shouldReturnNewRecipe() throws Exception {
        when(recipeServiceMock.findRecipeById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void shouldCreateNewRecipe() throws Exception {
        RecipeCommandObject recipeCommandObject = new RecipeCommandObject();
        recipeCommandObject.setId(2L);

        when(recipeServiceMock.saveRecipe(any())).thenReturn(recipeCommandObject);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some tips :)")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void shouldDeleteRecipe() throws Exception {
        //when
        mockMvc.perform(delete("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        //then
        verify(recipeServiceMock, times(1)).removeRecipe(anyLong());
    }
}