package com.epam.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductModel  extends RepresentationModel<ProductModel> {
	private final long id;
	private final String name;
	private final double price;
	private final String currency;
	private final int quantity;
	private final List<CategoryModel> categories;
}
