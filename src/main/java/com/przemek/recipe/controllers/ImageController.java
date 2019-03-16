package com.przemek.recipe.controllers;

import com.przemek.recipe.services.ImageService;
import com.przemek.recipe.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandObjectById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String uploadImage( @PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);

        return "redirect:/recipe/" + recipeId + "/recipe";
    }

}
