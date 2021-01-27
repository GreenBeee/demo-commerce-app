package com.epam.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	@NotNull
	@Size(message = "name length must be between [1, 300] symbols", min = 1, max = 300)
	private String name;
}
