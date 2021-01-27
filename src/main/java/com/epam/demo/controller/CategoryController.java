package com.epam.demo.controller;

import com.epam.demo.assembler.CategoryRepresentationModelAssembler;
import com.epam.demo.assembler.ProductRepresentationModelAssembler;
import com.epam.demo.dto.CategoryDTO;
import com.epam.demo.entity.Category;
import com.epam.demo.entity.Product;
import com.epam.demo.model.CategoryModel;
import com.epam.demo.service.CategoryService;
import com.epam.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {
	private final CategoryService categoryService;
	private final ProductService productService;
	private final CategoryRepresentationModelAssembler categoryAssembler;

	@GetMapping
	public ResponseEntity<CollectionModel<CategoryModel>> getAllCategories() {
		List<Category> allCategories = categoryService.getAllCategories();
		return ResponseEntity.ok(categoryAssembler.toCollectionModel(allCategories));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<RepresentationModel<CategoryModel>> getCategoryById(@PathVariable Long id) {
		final Category category = getCategoryOrThrowException(id);
		return ResponseEntity.ok(categoryAssembler.toModel(category));
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<CategoryModel>> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
		final Category category = categoryService.createCategory(categoryDTO.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryAssembler.toModel(category));
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<CategoryModel>> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
		final Category category = getCategoryOrThrowException(id);
		categoryService.updateCategory(category, categoryDTO.getName());
		return ResponseEntity.ok(categoryAssembler.toModel(category));
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<CategoryModel>> deleteCategory(@PathVariable Long id) {
		final Category category = getCategoryOrThrowException(id);
		categoryService.deleteCategory(category);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(path = "/{parentId}/subcategories")
	public ResponseEntity<CollectionModel<CategoryModel>> getAllSubcategories(@PathVariable Long parentId) {
		final Category parent = getCategoryOrThrowException(parentId);
		final Set<Category> subcategories = parent.getChildCategories();

		return ResponseEntity.ok(categoryAssembler.toCollectionModel(subcategories));
	}

	@PostMapping("/{categoryId}/product/{productId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<CategoryModel>> addProductToCategory(@PathVariable Long categoryId,
																				   @PathVariable Long productId) {
		final Category category = getCategoryOrThrowException(categoryId);
		final Product product = getProductOrThrowException(productId);
		productService.addCategory(product, category);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryAssembler.toModel(category));
	}

	@DeleteMapping("/{categoryId}/product/{productId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<CategoryModel>> removeProductFromCategory(@PathVariable Long categoryId,
																				   @PathVariable Long productId) {
		final Category category = getCategoryOrThrowException(categoryId);
		final Product product = getProductOrThrowException(productId);
		productService.removeCategory(product, category);
		return ResponseEntity.noContent().build();
	}

	private Category getCategoryOrThrowException(Long id) {
		return categoryService.getCategoryById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Category not found"));
	}

	private Product getProductOrThrowException(Long id) {
		return productService.getProductById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Product not found"));
	}
}
