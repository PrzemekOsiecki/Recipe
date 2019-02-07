package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/recipe/{recipeId}/show", method = RequestMethod.GET)
    String getRecipeById(@PathVariable long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeById(recipeId));
        return "recipe/recipe";
    }

    @RequestMapping(value = "/recipe/new", method = RequestMethod.GET)
    String getNewRecipe (Model model) {
        model.addAttribute("recipe", new RecipeCommandObject());
        return "recipe/recipeform";
    }

    @RequestMapping(value = "recipe", method = RequestMethod.POST)
    String saveOrUpdateRecipe(@ModelAttribute RecipeCommandObject recipeCommandObject) {
        RecipeCommandObject savedRecipeCommandObject = recipeService.saveRecipe(recipeCommandObject);
        return "redirect:/recipe/" + savedRecipeCommandObject.getId() + "/show";
    }

    @RequestMapping(value = "/recipe/{recipeId}/delete", method = RequestMethod.DELETE)
    String deleteRecipe(@PathVariable long recipeId) {
        recipeService.removeRecipe(recipeId);
        return "redirect:/";
    }
}
