package com.epam.demo.service;

import com.epam.demo.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

	List<Category> getAllCategories();

	Optional<Category> getCategoryById(Long id);

	Category createCategory(String name);

	void updateCategory(Category category, String name);

	void deleteCategory(Category category);
}
