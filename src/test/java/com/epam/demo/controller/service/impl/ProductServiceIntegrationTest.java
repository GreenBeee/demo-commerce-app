package com.epam.demo.controller.service.impl;

import com.epam.demo.dao.ProductRepository;
import com.epam.demo.entity.Currency;
import com.epam.demo.entity.Product;
import com.epam.demo.service.ExchangeService;
import com.epam.demo.service.ProductService;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(
		locations = "classpath:application.properties")
public class ProductServiceIntegrationTest {
	private static final int MOCK_QUANTITY = 1;
	private static final double MOCK_EUR_PRICE = 100;
	private static final double MOCK_USD_PRICE = 50;
	private static final String EUR_CONSTANT = "EUR";
	private static final String USD_CONSTANT = "USD";
	private static final String MOCK_TEST_NAME_ONE = "Test name one";
	private static final String MOCK_TEST_NAME_TWO = "Test name two";

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ExchangeService exchangeService;

	@Test
	public void testRetrievingProduct(){
		//given
		Product product = saveProduct();
		//when
		Optional<Product> productById = productService.getProductById(product.getId());
		//then
		assertTrue(productById.isPresent());
		assertEquals(product.getName(), productById.get().getName());
		assertEquals(product.getPrice(), productById.get().getPrice());
		assertEquals(product.getCurrency(), productById.get().getCurrency());
		assertEquals(product.getQuantity(), productById.get().getQuantity());
	}

	@Test
	public void testCreationOfProductWithEURCurrency() {
		//when
		Product product = productService.createProduct(MOCK_TEST_NAME_ONE, EUR_CONSTANT, MOCK_EUR_PRICE, MOCK_QUANTITY);

		//then
		assertEquals(MOCK_TEST_NAME_ONE, product.getName());
		assertEquals(EUR_CONSTANT, product.getCurrency().name());
		assertEquals(Double.valueOf(MOCK_EUR_PRICE), product.getPrice());
		assertEquals(MOCK_QUANTITY, product.getQuantity().intValue());
	}

	@Test
	public void testCreationOfProductWithUSDCurrency() {
		//given
		double usdRate = exchangeService.getRateForCurrency(Currency.EUR, "USD");
		//when
		Product product = productService.createProduct(MOCK_TEST_NAME_ONE, USD_CONSTANT, MOCK_USD_PRICE, MOCK_QUANTITY);
		//then
		assertEquals(MOCK_TEST_NAME_ONE, product.getName());
		assertEquals(EUR_CONSTANT, product.getCurrency().name());
		double expectedValue = Math.round((MOCK_USD_PRICE / usdRate) * 100.0) / 100.0;
		assertEquals(Double.valueOf(expectedValue), product.getPrice());
		assertEquals(MOCK_QUANTITY, product.getQuantity().intValue());
	}


	@Test
	public void testUpdateOfProductWithUSDCurrency() {
		//given
		double usdRate = exchangeService.getRateForCurrency(Currency.EUR, USD_CONSTANT);
		Product productToSave = saveProduct();

		//when
		productService.updateProduct(productToSave, MOCK_TEST_NAME_ONE, USD_CONSTANT, MOCK_USD_PRICE, MOCK_QUANTITY);

		//then
		Optional<Product> productById = productRepository.findById(productToSave.getId());
		double expectedValue = Math.round((MOCK_USD_PRICE / usdRate) * 100.0) / 100.0;
		assertTrue(productById.isPresent());
		assertEquals(MOCK_TEST_NAME_ONE, productById.get().getName());
		assertEquals(Double.valueOf(expectedValue), productById.get().getPrice());
		assertEquals(MOCK_QUANTITY, productById.get().getQuantity().intValue());
	}

	@Test
	public void testDeletingProduct(){
		//given
		Product product = saveProduct();
		//when
		productService.deleteProduct(product);
		//then
		assertTrue(productRepository.findAll().isEmpty());
	}

	private Product saveProduct() {
		Product productToSave = new Product();
		productToSave.setCurrency(Currency.EUR);
		productToSave.setName(MOCK_TEST_NAME_TWO);
		productToSave.setPrice(MOCK_EUR_PRICE);
		productToSave.setQuantity(MOCK_QUANTITY);
		productRepository.save(productToSave);
		return productToSave;
	}

	@After
	public void cleanUp() {
		productRepository.deleteAll();
	}
}
