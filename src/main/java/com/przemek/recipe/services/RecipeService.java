package com.przemek.recipe.services;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.converters.RecipeCommandObjectToRecipeConverter;
import com.przemek.recipe.converters.RecipeToRecipeCommandObjectConverter;
import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeService {

    RecipeRepository recipeRepository;
    RecipeCommandObjectToRecipeConverter recipeCommandObjectToRecipeConverter;
    RecipeToRecipeCommandObjectConverter recipeToRecipeCommandObjectConverter;

    public RecipeService(RecipeRepository recipeRepository,
                         RecipeCommandObjectToRecipeConverter recipeCommandObjectToRecipeConverter,
                         RecipeToRecipeCommandObjectConverter recipeToRecipeCommandObjectConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandObjectToRecipeConverter = recipeCommandObjectToRecipeConverter;
        this.recipeToRecipeCommandObjectConverter = recipeToRecipeCommandObjectConverter;
    }

    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        log.info("fetching recipes");
        return recipes;
    }

    public Recipe findRecipeById(long id) {
        return recipeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public RecipeCommandObject saveRecipe(RecipeCommandObject recipeCommandObject) {
        Recipe detachedRecipe = recipeCommandObjectToRecipeConverter.convert(recipeCommandObject);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("saved Recipe with id "+ savedRecipe.getId());
        return recipeToRecipeCommandObjectConverter.convert(savedRecipe);
    }
}
