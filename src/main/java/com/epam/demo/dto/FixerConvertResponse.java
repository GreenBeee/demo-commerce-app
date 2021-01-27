package com.epam.demo.dto;

import lombok.Data;

@Data
public class FixerConvertResponse {
	private Boolean success;
	private Double result;
	private FixerConvertQuery query;
	private FixerInfo info;
	private String date;
}
