package com.przemek.recipe.controllers;

import com.przemek.recipe.services.IngredientService;
import com.przemek.recipe.services.RecipeService;
import com.przemek.recipe.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@Controller
public class IngredientsController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UnitOfMeasureService unitOfMeasureService;

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients")
    String getIngredients(@PathVariable String recipeId, Model model) {
        log.debug("getting ingredients for recipe with id " + recipeId);
        model.addAttribute("recipe", recipeService.findRecipeCommandObjectById(Long.parseLong(recipeId)));

        return "recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    String showInngredient(@PathVariable String recipeId,
                           @PathVariable("id") String ingredientId,
                           Model model) {

        model.addAttribute("ingredient", ingredientService
                .findIngredientByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredients/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }
}
