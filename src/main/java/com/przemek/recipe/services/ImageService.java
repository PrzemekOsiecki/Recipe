package com.przemek.recipe.services;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ImageService {

    RecipeRepository recipeRepository;

    @Transactional
    public void saveImageFile(long recipeId, MultipartFile image) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        recipeOptional.ifPresent(recipe -> {
            try {
                Byte[] byteObject = new Byte[image.getBytes().length];

                int i = 0;
                for (byte b : image.getBytes()) {
                    byteObject[i++] = b;
                }

                recipe.setImage(byteObject);

                recipeRepository.save(recipe);
                log.debug("Saving image");
            } catch (IOException e) {
                log.error("cannot open image file");
            }
        });
    }
}
