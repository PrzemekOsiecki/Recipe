package com.przemek.recipe.converters;

import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandObjectConverterTest {

    UnitOfMeasureToUnitOfMeasureCommandObjectConverter uut = new UnitOfMeasureToUnitOfMeasureCommandObjectConverter();

    @Test
    public void shouldConvertEmptyObject() throws Exception {
        assertNotNull(uut.convert(new UnitOfMeasure()));
    }

    @Test
    public void shouldConvertUnitOfMeasure() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(new Long(1L));
        uom.setDescription("description");
        //when
        UnitOfMeasureCommandObject uomc = uut.convert(uom);

        //then
        assertEquals(uom.getId(), uomc.getId());
        assertEquals(uom.getDescription(), uomc.getDescription());
    }
}