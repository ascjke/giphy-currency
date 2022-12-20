package ru.borisov.giphycurrency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GifDto {
    private CurrencyStatus currencyStatus;
    private String gifUrl;
    private String currency;

    public GifDto(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
