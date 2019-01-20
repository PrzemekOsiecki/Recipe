package com.przemek.recipe.controllers;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.services.RecipeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    IndexController uut;

    @Mock
    Model modelMock;

    @Mock
    RecipeService recipeServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        uut = new IndexController(recipeServiceMock);
    }

    @Test
    public void shouldReturnHttpStatusOk() throws Exception {
        //given
//        Set<Recipe> expectedRecipes = new HashSet<>();
//        Recipe recipe = new Recipe();
//        recipe.setId(1L);
//        expectedRecipes.add(recipe);

        //when
//        when(recipeServiceMock.getAllRecipes()).thenReturn(expectedRecipes);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(uut).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("recipe",
//                        Matchers.hasItems(hasProperty("id"), Matchers.equalTo(1L))))
                .andExpect(view().name("index"));
    }

    @Test
    public void shouldReturnIndexPage() {
        //given
        final String expectedViewName = "index";
        final String recipes = "recipes";

        Set<Recipe> expectedRecipes = new HashSet<>();
        expectedRecipes.add(new Recipe());
        Recipe anotherRecipe = new Recipe();
        anotherRecipe.setId(10L);
        expectedRecipes.add(anotherRecipe);

        //when
        when(recipeServiceMock.getAllRecipes()).thenReturn(expectedRecipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String actualPageName = uut.getIndexPage(modelMock);

        //then
        assertEquals(expectedViewName, actualPageName);
        verify(recipeServiceMock, times(1)).getAllRecipes();
//        verify(modelMock, times(1)).addAttribute(eq(recipes), anySet());
        verify(modelMock, times(1)).addAttribute(eq(recipes), argumentCaptor.capture());
        assertEquals(expectedRecipes, argumentCaptor.getValue());
        assertEquals(2, argumentCaptor.getValue().size());
    }
}