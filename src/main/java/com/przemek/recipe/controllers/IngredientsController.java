package com.przemek.recipe.controllers;

import com.przemek.recipe.commands.IngredientCommandObject;
import com.przemek.recipe.commands.RecipeCommandObject;
import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.services.IngredientService;
import com.przemek.recipe.services.RecipeService;
import com.przemek.recipe.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Controller
public class IngredientsController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UnitOfMeasureService unitOfMeasureService;

    @GetMapping("recipe/{recipeId}/ingredients")
    String getIngredients(@PathVariable String recipeId, Model model) {
        log.debug("getting ingredients for recipe with id " + recipeId);
        model.addAttribute("recipe", recipeService.findRecipeCommandObjectById(Long.parseLong(recipeId)));

        return "recipe/ingredients/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    String showInngredient(@PathVariable String recipeId,
                           @PathVariable("id") String ingredientId,
                           Model model) {

        model.addAttribute("ingredient", ingredientService
                .findIngredientByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredients/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findIngredientByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredients/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        RecipeCommandObject recipeCommand = recipeService.findRecipeCommandObjectById(Long.valueOf(recipeId));

        //need to return back parent id for hidden form property
        IngredientCommandObject ingredientCommand = new IngredientCommandObject();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommandObject());

        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms());

        return "recipe/ingredients/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommandObject command) {
        IngredientCommandObject savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @DeleteMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        log.debug("DADadDADAD");

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
