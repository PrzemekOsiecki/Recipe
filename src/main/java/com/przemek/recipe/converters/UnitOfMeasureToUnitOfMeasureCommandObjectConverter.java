package com.przemek.recipe.converters;


import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommandObjectConverter implements Converter<UnitOfMeasure, UnitOfMeasureCommandObject> {

    @Override
    public UnitOfMeasureCommandObject convert(UnitOfMeasure unitOfMeasure) {
        final UnitOfMeasureCommandObject unitOfMeasureCommandObject = new UnitOfMeasureCommandObject();
        unitOfMeasureCommandObject.setId(unitOfMeasure.getId());
        unitOfMeasureCommandObject.setDescription(unitOfMeasure.getDescription());
        return unitOfMeasureCommandObject;
    }
}
