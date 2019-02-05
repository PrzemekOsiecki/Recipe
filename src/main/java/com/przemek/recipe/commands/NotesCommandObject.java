package com.przemek.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommandObject {

    private Long id;
    private String recipeNotes;
}
