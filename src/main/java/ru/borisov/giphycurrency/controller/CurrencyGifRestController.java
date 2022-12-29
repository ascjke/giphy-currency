package ru.borisov.giphycurrency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("/api/currencyGif")
@RequiredArgsConstructor
public class CurrencyGifRestController {

    private final GifService gifService;
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<CurrencyGifDto> getCurrencyGifDto() {
        CurrencyGifDto currencyGifDto = currencyService.getCurrencyGifDto();
        String gifUrl = gifService.getGifUrl(currencyGifDto.getCurrencyStatus());
        currencyGifDto.setGifUrl(gifUrl);

        return new ResponseEntity<>(currencyGifDto, HttpStatus.OK);
    }
}
