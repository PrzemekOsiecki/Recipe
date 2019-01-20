package com.przemek.recipe.repositories;

import com.przemek.recipe.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void shouldReturnTeaspoonAsUnitOfMeasure() {
        //given
        final String teaspoonDescription = "Teaspoon";
        //when
        Optional<UnitOfMeasure> teaspoonUoM = unitOfMeasureRepository.findByDescription(teaspoonDescription);
        //then
        assertEquals(teaspoonDescription, teaspoonUoM.get().getDescription());

    }
}