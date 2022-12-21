package ru.borisov.giphycurrency.dto;

import lombok.Data;

@Data
public class GifDto {
    private CurrencyStatus currencyStatus;
    private String gifUrl;
    private String currency;

    public GifDto(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
