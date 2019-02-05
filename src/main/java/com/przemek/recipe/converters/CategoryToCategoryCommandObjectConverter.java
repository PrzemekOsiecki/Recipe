package com.przemek.recipe.converters;

import com.przemek.recipe.commands.CategoryCommandObject;
import com.przemek.recipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommandObjectConverter implements Converter<Category, CategoryCommandObject> {

    @Override
    public CategoryCommandObject convert(Category category) {
        final CategoryCommandObject categoryCommandObject = new CategoryCommandObject();
        categoryCommandObject.setId(category.getId());
        categoryCommandObject.setDescription(category.getDescription());
        return categoryCommandObject;
    }
}
