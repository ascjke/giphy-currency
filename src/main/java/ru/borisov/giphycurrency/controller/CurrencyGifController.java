package ru.borisov.giphycurrency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.borisov.giphycurrency.dto.CurrencyStatus;
import ru.borisov.giphycurrency.dto.GifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;

@Controller
@RequestMapping(value = "/currency")
@RequiredArgsConstructor
public class CurrencyGifController {

    private static final String[] CURRENCY_STATUSES = {"rich", "broke"};
    private final GifService gifService;
    private final CurrencyService currencyService;

    @GetMapping("/gif")
    public String getCurrencyGifView(Model model) {
        long start = System.currentTimeMillis();
        String currencyRateStatus = currencyService.getCurrencyRateStatus();
        GifDto gifDto = getGifDto(currencyRateStatus);
        long responseTime = System.currentTimeMillis() - start;
        model.addAttribute("gifDto", gifDto);
        model.addAttribute("responseTime", responseTime);

        return "currency_gif";
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
