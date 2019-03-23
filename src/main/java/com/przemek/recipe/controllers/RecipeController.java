package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.exceptions.NotFoundException;
import com.przemek.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
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
    String getNewRecipeForm (Model model) {
        model.addAttribute("recipe", new RecipeCommandObject());
        return "recipe/recipeform";
    }

    @RequestMapping(value = "recipe", method = RequestMethod.POST)
    String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommandObject recipeCommandObject,  BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return  "recipe/recipeform";
        }

        RecipeCommandObject savedRecipeCommandObject = recipeService.saveRecipe(recipeCommandObject);
        return "redirect:/recipe/" + savedRecipeCommandObject.getId() + "/show";
    }

    @RequestMapping(value = "/recipe/{recipeId}/delete", method = RequestMethod.DELETE)
    String deleteRecipe(@PathVariable long recipeId) {
        recipeService.removeRecipe(recipeId);
        return "redirect:/";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandObjectById(Long.valueOf(id)));
        return  "recipe/recipeform";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ModelAndView notFoundHandler() {
        log.error("Recipe not found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        return modelAndView;
    }
}
