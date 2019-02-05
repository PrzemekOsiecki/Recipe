package com.przemek.recipe.converters;

import com.przemek.recipe.commands.NotesCommandObject;
import com.przemek.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommandObjectConverter implements Converter<Notes, NotesCommandObject> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommandObject convert(Notes notes) {
        final NotesCommandObject notesCommandObject = new NotesCommandObject();
        notesCommandObject.setId(notes.getId());
        notesCommandObject.setRecipeNotes(notes.getRecipeNotes());
        return notesCommandObject;
    }
}
