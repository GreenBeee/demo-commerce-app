package com.epam.demo.service.impl;

import com.epam.demo.dto.FixerConvertResponse;
import com.epam.demo.dto.FixerRatesResponse;
import com.epam.demo.entity.Currency;
import com.epam.demo.exception.FixerException;
import com.epam.demo.service.ExchangeService;
import com.epam.demo.service.FixerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FixerExchangeServiceImpl implements ExchangeService {
	private final FixerService fixerService;

	@Override
	public double convert(double amount, Currency from, Currency to) {
		if (to != Currency.EUR) {
			throw new IllegalArgumentException("Only " + Currency.EUR + " currency conversion is supported");
		}
		FixerConvertResponse convertResponse = fixerService.getConvertResponse(amount, from, to);
		return convertResponse.getResult();
	}

	@Override
	public Map<String, Double> latestRates(Currency base) {
		FixerRatesResponse rateResponse = fixerService.getRateResponse(base);
		if (!rateResponse.getSuccess()){
			throw new FixerException("Issue encountered with Fixer IO API");
		}
		return rateResponse.getRates();
	}

	@Override
	public double getRateForCurrency(Currency base, String current) {
		Double rate = latestRates(base).get(current);
		if (rate == null) {
			throw new IllegalArgumentException("Currency " + current + " is not supported");
		}
		return rate;
	}
}
