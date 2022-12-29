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
    private double yesterdayExchangeRate;
    private double latestExchangeRate;

    @Override
    public String getCurrencyApiKey() {
        return exchangeRateRepository.getCurrencyApiKey();
    }

    @Override
    public String getCurrency() {
        return exchangeRateRepository.getCurrency();
    }


    public CurrencyGifDto getCurrencyGifDto() {
        CompletableFuture.runAsync(this::getYesterdayExchangeRate);
        CompletableFuture.runAsync(this::getLatestExchangeRate);

//        double yesterdayExchangeRate = getYesterdayExchangeRate().join();
//        double latestExchangeRate = getLatestExchangeRate().join();

        try {
            Thread.sleep(1400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
    public void getLatestExchangeRate() {
        latestExchangeRate = exchangeRateRepository.getLatestExchangeRate();
//        return CompletableFuture.completedFuture(exchangeRateRepository.getLatestExchangeRate());
    }

    @Override
    @Async
    public void getYesterdayExchangeRate() {
        yesterdayExchangeRate = exchangeRateRepository.getYesterdayExchangeRate();
//        return CompletableFuture.completedFuture(exchangeRateRepository.getYesterdayExchangeRate());

    }
}
