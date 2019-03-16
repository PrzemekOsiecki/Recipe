package com.przemek.recipe.services;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {

    @Mock
    RecipeRepository recipeRepositoryMock;

    ImageService uut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        uut = new ImageService(recipeRepositoryMock);
    }

    @Test
    public void shouldSaveImageFile() throws IOException {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Learning".getBytes());

        when(recipeRepositoryMock.findById(anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        uut.saveImageFile(1L, multipartFile);

        //then
        verify(recipeRepositoryMock, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}