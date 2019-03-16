package com.przemek.recipe.converters;

import com.przemek.recipe.commands.NotesCommandObject;
import com.przemek.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandObjectToNotesConverter implements Converter<NotesCommandObject, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommandObject notesCommandObject) {
        final Notes notes = new Notes();
//        notes.setId(notesCommandObject.getId());
        notes.setRecipeNotes(notesCommandObject.getRecipeNotes());
        return notes;
    }
}
