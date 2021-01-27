package com.epam.demo.assembler;

import com.epam.demo.controller.CategoryController;
import com.epam.demo.entity.Category;
import com.epam.demo.model.CategoryModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepresentationModelAssembler implements RepresentationModelAssembler<Category, CategoryModel> {
	@Override
	public CategoryModel toModel(Category entity) {
		CategoryModel categoryResource = new CategoryModel(entity.getName(), entity.getId());
		if (entity.getParent() != null) {
			categoryResource.add(linkTo(methodOn(CategoryController.class)
					.getCategoryById(entity.getParent().getId())).withRel("parent"));
		}
		if (entity.getChildCategories() != null) {
			categoryResource.add(linkTo(methodOn(CategoryController.class)
					.getAllSubcategories(entity.getId())).withRel("subcategories"));
		}
		return categoryResource;
	}
}
