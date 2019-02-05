package com.przemek.recipe.commands;

import com.przemek.recipe.domain.Recipe;
import com.przemek.recipe.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommandObject {

    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommandObject unitOfMeasure;
}
