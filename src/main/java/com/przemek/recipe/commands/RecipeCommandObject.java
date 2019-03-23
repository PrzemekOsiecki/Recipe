package com.przemek.recipe.commands;

import com.przemek.recipe.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommandObject {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    private Integer servings;

    @URL
    private String url;

    @NotBlank
    private String directions;
    private Set<IngredientCommandObject> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommandObject notes;
    private Set<CategoryCommandObject> categories = new HashSet<>();

}
