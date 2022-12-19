package ru.borisov.giphycurrency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GifDto {
    private boolean isUpForUSD;
    private String gifUrl;
    private String currency;

    public GifDto(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
