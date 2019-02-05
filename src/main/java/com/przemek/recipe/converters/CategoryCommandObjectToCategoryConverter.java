package com.przemek.recipe.converters;

import com.przemek.recipe.commands.CategoryCommandObject;
import com.przemek.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandObjectToCategoryConverter implements Converter<CategoryCommandObject, Category> {

    @Override
    public Category convert(CategoryCommandObject categoryCommandObject) {
        final Category category = new Category();
        category.setId(categoryCommandObject.getId());
        category.setDescription(categoryCommandObject.getDescription());
        return category;
    }
}
