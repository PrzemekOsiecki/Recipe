package com.przemek.recipe.services;

import com.przemek.recipe.commands.UnitOfMeasureCommandObject;
import com.przemek.recipe.converters.UnitOfMeasureToUnitOfMeasureCommandObjectConverter;
import com.przemek.recipe.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@Service
public class UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommandObjectConverter unitOfMeasureToUnitOfMeasureCommand;

    public Set<UnitOfMeasureCommandObject> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
