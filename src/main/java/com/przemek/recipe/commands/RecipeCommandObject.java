package com.przemek.recipe.commands;

import com.przemek.recipe.domain.Category;
import com.przemek.recipe.domain.Difficulty;
import com.przemek.recipe.domain.Ingredient;
import com.przemek.recipe.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommandObject {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String url;
    private String directions;
    private Set<IngredientCommandObject> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommandObject notes;
    private Set<CategoryCommandObject> categories = new HashSet<>();

}
