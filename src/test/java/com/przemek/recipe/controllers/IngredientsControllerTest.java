package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.services.IngredientService;
import com.przemek.recipe.services.RecipeService;
import com.przemek.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientsControllerTest {

    @Mock
    RecipeService recipeServiceMock;

    @Mock
    IngredientService ingredientServiceMock;

    @Mock
    UnitOfMeasureService unitOfMeasureServiceMock;

    IngredientsController ingredientsController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ingredientsController = new IngredientsController(recipeServiceMock, ingredientServiceMock, unitOfMeasureServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientsController).build();
    }

    @Test
    public void shouldReturnListOfIngredients() throws Exception {
        //given
        RecipeCommandObject recipeCommandObject = new RecipeCommandObject();
        when(recipeServiceMock.findRecipeCommandObjectById(anyLong())).thenReturn(recipeCommandObject);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        //throws
        verify(recipeServiceMock, times(1)).findRecipeCommandObjectById(anyLong());
    }

    @Test
    public void shouldReturnIngredient() throws Exception {
        //given
        IngredientCommandObject ingredientCommandObject = new IngredientCommandObject();
        when(ingredientServiceMock.findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommandObject);

        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));

        //throws
        verify(ingredientServiceMock, times(1)).findIngredientByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommandObject command = new IngredientCommandObject();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        when(ingredientServiceMock.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void shouldDeleteIngredient() throws Exception{
        mockMvc.perform(delete("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientServiceMock, times(1)).deleteIngredient(anyLong(), anyLong());
    }

}