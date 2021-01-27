package com.epam.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@Getter
@Setter
public class CategoryModel  extends RepresentationModel<CategoryModel> {
	private final String name;
	private final Long id;
}
