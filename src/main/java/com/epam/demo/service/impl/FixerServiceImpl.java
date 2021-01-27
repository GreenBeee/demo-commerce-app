package com.epam.demo.service.impl;

import com.epam.demo.dto.FixerConvertResponse;
import com.epam.demo.dto.FixerRatesResponse;
import com.epam.demo.entity.Currency;
import com.epam.demo.service.FixerService;
import static javax.management.timer.Timer.ONE_DAY;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FixerServiceImpl implements FixerService {
	private static final String ACCESS_KEY_PARAM = "access_key";
	private static final String FROM_PARAM = "from";
	private static final String BASE_PARAM = "base";
	private static final String TO_PARAM = "to";
	private static final String AMOUNT_PARAM = "amount";

	private final RestTemplate restTemplate;

	@Value("${fixer.api.access-key}")
	private String accessKey;

	@Value("${fixer.api.base-url}")
	private String fixerBaseUrl;

	@Value("${fixer.api.convert-uri}")
	private String fixerConvertUri;

	@Value("${fixer.api.latest-uri}")
	private String fixerLatestUri;

	@Override
	public FixerConvertResponse getConvertResponse(double amount, Currency from, Currency to) {
		validateAccessKey();
		UriComponents build = UriComponentsBuilder.fromHttpUrl(fixerBaseUrl + fixerConvertUri)
				.queryParam(ACCESS_KEY_PARAM, accessKey)
				.queryParam(FROM_PARAM, from.name())
				.queryParam(TO_PARAM, to.name())
				.queryParam(AMOUNT_PARAM, String.valueOf(amount))
				.build();
		return restTemplate.getForObject(build.toUriString(), FixerConvertResponse.class);
	}

	@Override
	@Cacheable("rates")
	public FixerRatesResponse getRateResponse(Currency base) {
		validateAccessKey();
		UriComponents build = UriComponentsBuilder.fromHttpUrl(fixerBaseUrl + fixerLatestUri)
				.queryParam(ACCESS_KEY_PARAM, accessKey)
				.queryParam(BASE_PARAM, base.name())
				.build();
		return restTemplate.getForObject(build.toUriString(), FixerRatesResponse.class);
	}

	private void validateAccessKey() {
		if (accessKey == null) {
			throw new IllegalArgumentException("Missing Fixer IO API access key");
		}
	}

	@Scheduled(fixedRate = ONE_DAY)
	@CacheEvict(value = "rates")
	public void clearCache() {
	}
}
