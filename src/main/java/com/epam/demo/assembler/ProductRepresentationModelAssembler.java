package com.epam.demo.assembler;

import com.epam.demo.entity.Product;
import com.epam.demo.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProductRepresentationModelAssembler implements RepresentationModelAssembler<Product, ProductModel> {
	private final CategoryRepresentationModelAssembler categoryResourceAssembler;

	@Override
	public ProductModel toModel(Product entity) {
		return new ProductModel(entity.getId(), entity.getName(), entity.getPrice(), entity.getCurrency().toString(), entity.getQuantity(),
				!CollectionUtils.isEmpty(entity.getCategories()) ?
						new ArrayList<>(categoryResourceAssembler.toCollectionModel(entity.getCategories()).getContent())
						: null
		);
	}
}
