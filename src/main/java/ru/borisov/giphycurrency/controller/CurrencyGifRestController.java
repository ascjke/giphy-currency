package ru.borisov.giphycurrency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisov.giphycurrency.dto.CurrencyStatus;
import ru.borisov.giphycurrency.dto.GifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;


@RestController
@RequestMapping("/api/currencyGif")
@RequiredArgsConstructor
public class CurrencyGifRestController {

    private static final String[] CURRENCY_STATUSES = {"rich", "broke"};
    private final GifService gifService;
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<GifDto> getGifUrl() {
        String currencyRateStatus = currencyService.getCurrencyRateStatus();
        GifDto gifDto = getGifDto(currencyRateStatus);

        return new ResponseEntity<>(gifDto, HttpStatus.OK);
    }

    private GifDto getGifDto(String currencyRateStatus) {
        GifDto gifDto = new GifDto(gifService.getGifUrl(currencyRateStatus));
        gifDto.setCurrency(currencyService.getCurrency());
        if (CURRENCY_STATUSES[0].equals(currencyRateStatus)) {
            gifDto.setCurrencyStatus(CurrencyStatus.UP);
        } else if (CURRENCY_STATUSES[1].equals(currencyRateStatus)) {
            gifDto.setCurrencyStatus(CurrencyStatus.DOWN);
        } else {
            gifDto.setCurrencyStatus(CurrencyStatus.UNALTERED);
        }

        return gifDto;
    }
}
