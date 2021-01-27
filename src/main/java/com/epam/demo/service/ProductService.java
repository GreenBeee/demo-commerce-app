package com.epam.demo.service;

import com.epam.demo.entity.Category;
import com.epam.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

	Page<Product> getAllProducts(Pageable page);

	Optional<Product> getProductById(Long id);

	Product createProduct(String name, String currency, double price, int quantity);

	void updateProduct(Product product, String name, String currency, double price, int quantity);

	void deleteProduct(Product product);

	void addCategory(Product product, Category category);

	void removeCategory(Product product, Category category);
}
