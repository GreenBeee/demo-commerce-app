package com.epam.demo.controller;

import com.epam.demo.assembler.ProductRepresentationModelAssembler;
import com.epam.demo.dto.ProductDTO;
import com.epam.demo.entity.Product;
import com.epam.demo.model.ProductModel;
import com.epam.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductController {
	private final ProductService productService;
	private final ProductRepresentationModelAssembler assembler;
	private final PagedResourcesAssembler<Product> pagedAssembler;

	@GetMapping
	public ResponseEntity<PagedModel<ProductModel>> getAllProducts(Pageable pageable) {
		final Page<Product> products = productService.getAllProducts(pageable);
		return ResponseEntity.ok(pagedAssembler.toModel(products, assembler));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<RepresentationModel<ProductModel>> getProductById(@PathVariable Long id) {
		final Product product = getProductOrThrowException(id);
		return ResponseEntity.ok(assembler.toModel(product));
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<ProductModel>> createProduct(@RequestBody @Valid ProductDTO productDTO) {
		final Product product = productService.createProduct(productDTO.getName(),
				productDTO.getCurrency(),
				productDTO.getPrice(),
				productDTO.getQuantity());
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(product));
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<ProductModel>> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
		final Product product = getProductOrThrowException(id);
		productService.updateProduct(product, productDTO.getName(),
				productDTO.getCurrency(), productDTO.getPrice(), productDTO.getQuantity());
		return ResponseEntity.ok(assembler.toModel(product));
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<RepresentationModel<ProductModel>> deleteProduct(@PathVariable Long id) {
		final Product product = getProductOrThrowException(id);
		productService.deleteProduct(product);
		return ResponseEntity.noContent().build();
	}

	private Product getProductOrThrowException(Long id) {
		return productService.getProductById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Product not found"));
	}
}
