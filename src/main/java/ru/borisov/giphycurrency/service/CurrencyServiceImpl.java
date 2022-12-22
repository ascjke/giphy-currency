package ru.borisov.giphycurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.client.CurrencyClient;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.repository.ExchangeRateRepository;

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
    private final CurrencyGifDto currencyGifDto;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public String getCurrencyApiKey() {
        return exchangeRateRepository.getCurrencyApiKey();
    }

    @Override
    public String getCurrency() {
        return exchangeRateRepository.getCurrency();
    }

    public CurrencyGifDto getCurrencyGifDto() {
        double latestExchangeRate = getLatestExchangeRate();
        double yesterdayExchangeRate = getYesterdayExchangeRate();

        if (latestExchangeRate > yesterdayExchangeRate) {
            currencyGifDto.setCurrencyStatus(BROKE);
        } else if (latestExchangeRate < yesterdayExchangeRate) {
            currencyGifDto.setCurrencyStatus(RICH);
        } else {
            currencyGifDto.setCurrencyStatus(SO_SO);
        }
        currencyGifDto.setCurrency(getCurrency());

        return currencyGifDto;
    }

    @Override
    public double getLatestExchangeRate() {

        return exchangeRateRepository.getLatestExchangeRate();
    }

    @Override
    public double getYesterdayExchangeRate() {

        return exchangeRateRepository.getYesterdayExchangeRate();
    }
}
