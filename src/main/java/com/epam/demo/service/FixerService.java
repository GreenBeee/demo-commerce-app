package com.epam.demo.service;

import com.epam.demo.dto.FixerRatesResponse;
import com.epam.demo.entity.Currency;
import com.epam.demo.dto.FixerConvertResponse;

public interface FixerService {

	FixerConvertResponse getConvertResponse(double amount, Currency from, Currency to);

	FixerRatesResponse getRateResponse(Currency base);
}
