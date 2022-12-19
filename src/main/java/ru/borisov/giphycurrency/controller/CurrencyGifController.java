package ru.borisov.giphycurrency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisov.giphycurrency.dto.GifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;


@RestController
@RequestMapping("/api/checkCurrency")
@RequiredArgsConstructor
public class CurrencyGifController {

    private static final String RICH = "rich";
    private final GifService gifService;
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<GifDto> getGifUrl() {
        String currencyRateStatus = currencyService.getCurrencyRateStatus();
        GifDto gifDto = new GifDto(gifService.getGifUrl(currencyRateStatus));
        gifDto.setCurrency(currencyService.getCurrency());
        if (RICH.equals(currencyRateStatus)) {
            gifDto.setUpForUSD(true);
        }

        return new ResponseEntity<>(gifDto, HttpStatus.OK);
    }
}
