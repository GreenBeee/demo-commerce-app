package com.epam.demo.controller.service.impl;

import com.epam.demo.dao.ProductRepository;
import com.epam.demo.entity.Category;
import com.epam.demo.entity.Currency;
import com.epam.demo.entity.Product;
import com.epam.demo.service.impl.FixerExchangeServiceImpl;
import com.epam.demo.service.impl.ProductServiceImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceUnitTest {

	@Mock
	private ProductRepository productRepository;
	@Mock
	private FixerExchangeServiceImpl exchangeService;
	@InjectMocks
	private ProductServiceImpl productService;

	@Mock
	private Product mockProductOne;
	@Mock
	private Product mockProductTwo;
	@Mock
	private Category mockCategoryOne;

	private static final Long MOCK_ID_ONE = 1L;
	private static final Long MOCK_ID_TWO = 2L;
	private static final String MOCK_NAME_ONE = "Name1";
	private static final String MOCK_NAME_TWO = "Name2";
	private static final Double MOCK_RATE_FOR_USD = 1.3;


	@Before
	public void setUp() {
		when(mockProductOne.getId()).thenReturn(MOCK_ID_ONE);
		when(mockProductTwo.getId()).thenReturn(MOCK_ID_TWO);
		when(mockProductOne.getName()).thenReturn(MOCK_NAME_ONE);
		when(mockProductTwo.getName()).thenReturn(MOCK_NAME_TWO);
		when(productRepository.save(any(Product.class))).thenReturn(mockProductOne);
		when(productRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Arrays.asList(mockProductOne, mockProductTwo)));
	}

	@Test
	public void testRetrievingAllProducts() {
		Page<Product> allProducts = productService.getAllProducts(PageRequest.of(0, 5));

		assertEquals(2, allProducts.getContent().size());
		assertEquals(MOCK_NAME_ONE, allProducts.getContent().get(0).getName());
		assertEquals(MOCK_NAME_TWO, allProducts.getContent().get(1).getName());
		assertEquals(MOCK_ID_ONE, allProducts.getContent().get(0).getId());
		assertEquals(MOCK_ID_TWO, allProducts.getContent().get(1).getId());
	}

	@Test
	public void testCreationOfProduct() {
		String currentCurrency = Currency.USD.name();
		when(exchangeService.getRateForCurrency(Currency.EUR, currentCurrency)).thenReturn(MOCK_RATE_FOR_USD);

		Product product = productService.createProduct(MOCK_NAME_ONE, currentCurrency, 10, 1);

		verify(exchangeService, times(1)).getRateForCurrency(Currency.EUR, currentCurrency);
		assertEquals(MOCK_NAME_ONE, product.getName());
	}

	@Test
	public void testUpdatingProduct() {
		String currentCurrency = Currency.USD.name();
		when(exchangeService.getRateForCurrency(Currency.EUR, currentCurrency)).thenReturn(MOCK_RATE_FOR_USD);

		int mockPrice = 10;
		int mockQuantity = 1;
		productService.updateProduct(mockProductOne, MOCK_NAME_ONE, currentCurrency, mockPrice, mockQuantity);

		verify(exchangeService, times(1)).getRateForCurrency(Currency.EUR, currentCurrency);
		verify(mockProductOne, times(1)).setPrice(MOCK_RATE_FOR_USD * mockPrice);
		verify(mockProductOne, times(1)).setName(MOCK_NAME_ONE);
		verify(mockProductOne, times(1)).setQuantity(mockQuantity);
		verify(productRepository, times(1)).save(mockProductOne);
	}

	@Test
	public void testRemovingProduct() {
		productService.deleteProduct(mockProductOne);

		verify(productRepository, times(1)).delete(mockProductOne);
	}

	@Test
	public void testAddingNewCategoryToProduct() {
		when(mockProductOne.getCategories()).thenReturn(new HashSet<>());

		productService.addCategory(mockProductOne, mockCategoryOne);

		verify(productRepository, times(1)).save(mockProductOne);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddingExistedCategoryToProduct() {
		when(mockProductOne.getCategories()).thenReturn(Collections.singleton(mockCategoryOne));

		productService.addCategory(mockProductOne, mockCategoryOne);
	}
}
