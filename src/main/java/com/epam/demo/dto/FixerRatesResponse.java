package com.epam.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FixerRatesResponse {
	private Boolean success;
	private String timestamp;
	private String base;
	private String date;
	private Map<String, Double> rates;
}
