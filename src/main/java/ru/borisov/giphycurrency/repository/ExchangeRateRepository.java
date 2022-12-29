package ru.borisov.giphycurrency.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;
import ru.borisov.giphycurrency.client.CurrencyClient;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
@Log4j2
@Getter
public class ExchangeRateRepository {

    private static final String RICH = "rich";
    private static final String BROKE = "broke";
    private static final String SO_SO = "so-so";
    private static final int MINUS_DAYS = 1;
    @Value("${openexchangerates.api_key}")
    private String currencyApiKey;
    @Value("${openexchangerates.currency}")
    private String currency;
    private final CurrencyClient currencyClient;
    private final ObjectMapper mapper;
    private final CurrencyGifDto currencyGifDto;

//    @Cacheable(value = "latestExchangeRate")
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
        currencyGifDto.setLatestExchangeTime(actualTime);
        currencyGifDto.setLatestExchangeRate(latestExchangeRate);
        log.info("The latest (up to time {}) exchange rate of {} is: {}", actualTime, currency.toUpperCase(), latestExchangeRate);

        return latestExchangeRate;
    }

//    @Cacheable(value = "yesterdayExchangeRate")
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
        currencyGifDto.setYesterdayExchangeTime(closedTime);
        currencyGifDto.setYesterdayExchangeRate(yesterdayExchangeRate);
        log.info("The yesterday ({}) exchange rate of {} was: {}", closedTime, currency.toUpperCase(), yesterdayExchangeRate);

        return yesterdayExchangeRate;
    }
}
