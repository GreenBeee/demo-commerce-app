package com.epam.demo.service.impl;

import com.epam.demo.dao.ProductRepository;
import com.epam.demo.entity.Category;
import com.epam.demo.entity.Currency;
import com.epam.demo.entity.Product;
import com.epam.demo.service.ExchangeService;
import com.epam.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final ExchangeService exchangeService;

	@Override
	public Page<Product> getAllProducts(Pageable page) {
		return productRepository.findAll(page);
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product createProduct(String name, String currency, double price, int quantity) {
		if (!Currency.EUR.name().equals(currency)) {
			double rateForCurrency = exchangeService.getRateForCurrency(Currency.EUR, currency);
			price = price / rateForCurrency;
		}
		price = Math.round(price * 100.0) / 100.0;
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setQuantity(quantity);
		return productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product, String name, String currency, double price, int quantity) {
		if (!Currency.EUR.name().equals(currency)) {
			double rateForCurrency = exchangeService.getRateForCurrency(Currency.EUR, currency);
			price = price / rateForCurrency;
		}
		price = Math.round(price * 100.0) / 100.0;
		product.setName(name);
		product.setPrice(price);
		product.setQuantity(quantity);
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	@Override
	public void addCategory(Product product, Category category) {
		Set<Category> categories = product.getCategories();
		if (categories.contains(category)){
			throw new IllegalArgumentException("Product is already in this category");
		}
		categories.add(category);
		productRepository.save(product);
	}

	@Override
	public void removeCategory(Product product, Category category) {
		product.getCategories().remove(category);
		productRepository.save(product);
	}
}
