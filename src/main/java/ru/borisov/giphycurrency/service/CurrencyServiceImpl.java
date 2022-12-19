package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.client.CurrencyClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private static final String RICH = "rich";
    private static final String BROKE = "broke";
    private static final int MINUS_DAYS = 1;
    private final CurrencyClient currencyClient;
    private final ObjectMapper mapper;
    @Value("${openexchangerates.api_key}")
    private String currencyApiKey;
    @Value("${openexchangerates.currency}")
    private String currency;

    @Override
    public String getCurrencyRateStatus() {

        return getLatestExchangeRate() >= getYesterdayExchangeRate() ? RICH : BROKE;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    private double getLatestExchangeRate() {
        JsonNode node = null;
        log.info("Getting latest exchange rate from openexchangerates");
        String responseData = currencyClient.getLatestExchangeRate(currencyApiKey, currency);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        double latestExchangeRate =  node.get("rates").get(currency.toUpperCase()).asDouble();
        log.info("Latest exchange rate is: {}", latestExchangeRate);

        return latestExchangeRate;
    }

    private double getYesterdayExchangeRate() {
        JsonNode node = null;
        LocalDate yesterday = LocalDate.now().minusDays(MINUS_DAYS);
        log.info("Getting yesterday exchange rate from openexchangerates");
        String responseData = currencyClient.getYesterdayExchangeRate(yesterday.toString(),currencyApiKey, currency);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        double yesterdayExchangeRatenode = node.get("rates").get(currency.toUpperCase()).asDouble();
        log.info("Exchange rate yesterday({}) was: {}", yesterday, yesterdayExchangeRatenode);

        return  yesterdayExchangeRatenode;
    }
}
