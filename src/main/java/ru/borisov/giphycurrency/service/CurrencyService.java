package ru.borisov.giphycurrency.service;

import ru.borisov.giphycurrency.dto.CurrencyGifDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public interface CurrencyService {

    String getCurrency();
    String getCurrencyApiKey();
    CurrencyGifDto getCurrencyGifDto();
    CompletableFuture<Double> getLatestExchangeRate();
    CompletableFuture<Double> getYesterdayExchangeRate();
}
