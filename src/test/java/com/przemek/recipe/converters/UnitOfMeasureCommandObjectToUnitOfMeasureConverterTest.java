package com.przemek.recipe.converters;

import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandObjectToUnitOfMeasureConverterTest {

    UnitOfMeasureCommandObjectToUnitOfMeasureConverter uut = new UnitOfMeasureCommandObjectToUnitOfMeasureConverter();

    @Test
    public void shouldConvertEmptyObject() throws Exception {
        assertNotNull(uut.convert(new UnitOfMeasureCommandObject()));
    }

    @Test
    public void shouldConvertTOCommandObject() {
        //given
        UnitOfMeasureCommandObject uomc = new UnitOfMeasureCommandObject();
        uomc.setId(new Long(1L));
        uomc.setDescription("description");
        //when
        UnitOfMeasure uom = uut.convert(uomc);

        //then
        assertEquals(uomc.getId(), uomc.getId());
        assertEquals(uomc.getDescription(), uomc.getDescription());
    }
}