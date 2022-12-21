package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.client.CurrencyClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Log4j2
public class CurrencyServiceImpl implements CurrencyService {

    private static final String RICH = "rich";
    private static final String BROKE = "broke";
    private static final String SO_SO = "so-so";
    private static final int MINUS_DAYS = 1;
    private final CurrencyClient currencyClient;
    private final ObjectMapper mapper;
    @Value("${openexchangerates.api_key}")
    private String currencyApiKey;
    @Value("${openexchangerates.currency}")
    private String currency;

    @Override
    public String getCurrencyApiKey() {
        return currencyApiKey;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public String getCurrencyRateStatus() {
        double latestExchangeRate = getLatestExchangeRate();
        double yesterdayExchangeRate = getYesterdayExchangeRate();

        if (latestExchangeRate > yesterdayExchangeRate) {
            return BROKE;
        } else if (latestExchangeRate < yesterdayExchangeRate) {
            return RICH;
        } else {
            return SO_SO;
        }
    }

    @Override
    public double getLatestExchangeRate() {
        JsonNode node = null;
        log.info("Getting latest exchange rate from openexchangerates");
        String responseData = currencyClient.getLatestExchangeRate(currencyApiKey, currency);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        double latestExchangeRate = node.get("rates").get(currency.toUpperCase()).asDouble();
        long timestamp = node.get("timestamp").asLong();
        LocalDateTime actualTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());

        log.info("The latest (up to time {}) exchange rate of {} is: {}", actualTime, currency.toUpperCase(), latestExchangeRate);

        return latestExchangeRate;
    }

    @Override
    public double getYesterdayExchangeRate() {
        JsonNode node = null;
        LocalDate yesterday = LocalDate.now().minusDays(MINUS_DAYS);
        log.info("Getting yesterday exchange rate from openexchangerates");
        String responseData = currencyClient.getYesterdayExchangeRate(yesterday.toString(), currencyApiKey, currency);
        try {
            node = mapper.readTree(responseData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        double yesterdayExchangeRate = node.get("rates").get(currency.toUpperCase()).asDouble();
        long timestamp = node.get("timestamp").asLong();
        LocalDateTime closedTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());

        log.info("The yesterday ({}) exchange rate of {} was: {}", closedTime, currency.toUpperCase(), yesterdayExchangeRate);

        return yesterdayExchangeRate;
    }
}
