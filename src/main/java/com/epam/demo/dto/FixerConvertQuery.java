package com.epam.demo.dto;

import lombok.Data;

@Data
public class FixerConvertQuery {

	private String from;
	private String to;
	private Double amount;
}
