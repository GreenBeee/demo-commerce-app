package com.epam.demo.service;

import com.epam.demo.entity.Currency;

import java.util.Map;

public interface ExchangeService {

	double convert(double amount, Currency from, Currency to);

	Map<String, Double> latestRates(Currency base);

	double getRateForCurrency(Currency base, String current);
}
