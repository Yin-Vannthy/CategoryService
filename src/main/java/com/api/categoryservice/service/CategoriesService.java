package com.api.categoryservice.service;

import com.api.categoryservice.model.dto.CategoriesDTO;
import com.api.categoryservice.model.entity.Categories;
import com.api.categoryservice.model.request.CategoryRequest;

import java.util.List;

public interface CategoriesService {
    List<CategoriesDTO> getAllCategories();

    CategoriesDTO getCategoryById(Integer categoryId);

    Categories addNewCategory(CategoryRequest categoryRequest);

    Categories updateCategoryById(Integer categoryId, CategoryRequest categoryRequest);

    Boolean deleteCategoryId(Integer categoryId);
}
