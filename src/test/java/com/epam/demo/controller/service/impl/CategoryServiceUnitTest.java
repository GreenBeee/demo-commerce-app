package com.epam.demo.controller.service.impl;

import com.epam.demo.dao.CategoryRepository;
import com.epam.demo.entity.Category;
import com.epam.demo.service.impl.CategoryServiceImpl;
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

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceUnitTest {

	@Mock
	private CategoryRepository categoryRepository;
	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Mock
	private Category mockCategoryOne;
	@Mock
	private Category mockCategoryTwo;

	private static final Long MOCK_ID_ONE = 1L;
	private static final Long MOCK_ID_TWO = 2L;
	private static final String MOCK_NAME_ONE = "Name1";
	private static final String MOCK_NAME_TWO = "Name2";

	@Before
	public void setUp() {
		when(mockCategoryOne.getId()).thenReturn(MOCK_ID_ONE);
		when(mockCategoryOne.getName()).thenReturn(MOCK_NAME_ONE);
		when(mockCategoryTwo.getId()).thenReturn(MOCK_ID_TWO);
		when(mockCategoryTwo.getName()).thenReturn(MOCK_NAME_TWO);
		when(categoryRepository.save(any(Category.class))).thenReturn(mockCategoryOne);
		when(categoryRepository.findAll()).thenReturn(Arrays.asList(mockCategoryOne, mockCategoryTwo));
	}

	@Test
	public void testRetrievingAllCategories() {
		List<Category> allCategories = categoryService.getAllCategories();

		assertEquals(2, allCategories.size());
		assertEquals(MOCK_NAME_ONE, allCategories.get(0).getName());
		assertEquals(MOCK_NAME_TWO, allCategories.get(1).getName());
		assertEquals(MOCK_ID_ONE, allCategories.get(0).getId());
		assertEquals(MOCK_ID_TWO, allCategories.get(1).getId());
	}

	@Test
	public void testCreationOfCategory() {
		Category category = categoryService.createCategory(MOCK_NAME_ONE);

		assertEquals(MOCK_NAME_ONE, category.getName());
	}

	@Test
	public void testUpdatingCategory() {
		categoryService.updateCategory(mockCategoryOne, MOCK_NAME_ONE);

		verify(mockCategoryOne, times(1)).setName(MOCK_NAME_ONE);
		verify(categoryRepository, times(1)).save(mockCategoryOne);
	}

	@Test
	public void testRemovingCategory() {
		categoryService.deleteCategory(mockCategoryOne);

		verify(categoryRepository, times(1)).delete(mockCategoryOne);
	}
}
