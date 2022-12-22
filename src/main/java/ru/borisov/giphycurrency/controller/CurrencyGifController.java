package ru.borisov.giphycurrency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;

@Controller
@RequestMapping(value = "/currency")
@RequiredArgsConstructor
public class CurrencyGifController {

    private final GifService gifService;
    private final CurrencyService currencyService;

    @GetMapping("/gif")
    public String getCurrencyGifView(Model model) {
        long start = System.currentTimeMillis();
        CurrencyGifDto currencyGifDto = currencyService.getCurrencyGifDto();
        String gifUrl = gifService.getGifUrl(currencyGifDto.getCurrencyStatus());
        currencyGifDto.setGifUrl(gifUrl);
        long responseTime = System.currentTimeMillis() - start;
        model.addAttribute("currencyGifDto", currencyGifDto);
        model.addAttribute("responseTime", responseTime);

        return "currency_gif";
    }
}
