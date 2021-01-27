package com.epam.demo.service.impl;

import com.epam.demo.dao.CategoryRepository;
import com.epam.demo.entity.Category;
import com.epam.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category createCategory(String name) {
		Category category = new Category();
		category.setName(name);
		return categoryRepository.save(category);
	}

	@Override
	public void updateCategory(Category category, String name) {
		category.setName(name);
		categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}
}
