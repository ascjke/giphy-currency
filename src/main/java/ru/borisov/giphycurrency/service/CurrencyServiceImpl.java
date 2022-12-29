package ru.borisov.giphycurrency.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.repository.ExchangeRateRepository;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Log4j2
@EnableAsync
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
        double yesterdayExchangeRate = getYesterdayExchangeRate().join();
        double latestExchangeRate = getLatestExchangeRate().join();


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
    @Async
    public CompletableFuture<Double> getLatestExchangeRate() {

        return CompletableFuture.completedFuture(exchangeRateRepository.getLatestExchangeRate());
    }

    @Override
    @Async
    public CompletableFuture<Double> getYesterdayExchangeRate() {

        return CompletableFuture.completedFuture(exchangeRateRepository.getYesterdayExchangeRate());
    }
}
