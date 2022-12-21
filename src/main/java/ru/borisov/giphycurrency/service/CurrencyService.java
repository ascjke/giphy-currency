package ru.borisov.giphycurrency.service;

public interface CurrencyService {

    String getCurrency();
    String getCurrencyApiKey();
    String getCurrencyRateStatus();
    double getLatestExchangeRate();
    double getYesterdayExchangeRate();
}
