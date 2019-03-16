package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.services.ImageService;
import com.przemek.recipe.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageServiceMock;

    @Mock
    RecipeService recipeServiceMock;

    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageServiceMock, recipeServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageForm() throws Exception {
        //given
        RecipeCommandObject command = new RecipeCommandObject();
        command.setId(1L);

        when(recipeServiceMock.findRecipeCommandObjectById(anyLong())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeServiceMock, times(1)).findRecipeCommandObjectById(anyLong());

    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/recipe"));

        verify(imageServiceMock, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void shouldReadImageFromDB() throws Exception {
        //given
        RecipeCommandObject recipeCommandObject = new RecipeCommandObject();
        recipeCommandObject.setId(1L);

        String s = "Some image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;
        for (byte b : s.getBytes()) {
            bytesBoxed[i++] = b;
        }

        recipeCommandObject.setImage(bytesBoxed);

        when(recipeServiceMock.findRecipeCommandObjectById(anyLong())).thenReturn(recipeCommandObject);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        //then
        Assert.assertEquals(s.getBytes().length, responseBytes.length);
    }

}