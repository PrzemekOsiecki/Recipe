package com.przemek.recipe.converters;

import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandObjectToUnitOfMeasureConverter implements Converter<UnitOfMeasureCommandObject, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommandObject unitOfMeasureCommandObject) {
        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(unitOfMeasureCommandObject.getId());
        unitOfMeasure.setDescription(unitOfMeasureCommandObject.getDescription());
        return unitOfMeasure;
    }
}
