package ru.borisov.giphycurrency.service;

import ru.borisov.giphycurrency.dto.CurrencyGifDto;

public interface CurrencyService {

    String getCurrency();
    String getCurrencyApiKey();
    CurrencyGifDto getCurrencyGifDto();
    double getLatestExchangeRate();
    double getYesterdayExchangeRate();
}
