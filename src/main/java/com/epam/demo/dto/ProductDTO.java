package com.epam.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	@NotNull
	@Size(message = "name length must be between [1, 300] symbols", min = 1, max = 300)
	private String name;

	@NotNull
	@Size(message = "Currency must be in ISO 4217 format", min = 3, max = 3)
	private String currency;

	@NotNull
	@Min(0)
	private Double price;

	@NotNull
	@Min(0)
	private Integer quantity;
}
