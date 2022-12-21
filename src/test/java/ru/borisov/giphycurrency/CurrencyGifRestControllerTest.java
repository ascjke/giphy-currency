package ru.borisov.giphycurrency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.borisov.giphycurrency.controller.CurrencyGifRestController;
import ru.borisov.giphycurrency.dto.GifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CurrencyGifRestControllerTest {

    @Autowired
    CurrencyGifRestController restController;
    @MockBean
    GifService gifService;
    @MockBean
    CurrencyService currencyService;

    @Test
    public void getGifUrl() {

        when(currencyService.getCurrencyRateStatus()).thenReturn("rich");
        String gifUrl = "https://media1.giphy.com/media/LdOyjZ7io5Msw/giphy.gif?cid=1eb9b1efexdppscjglhe3xwsj5e1benua7zpego5cfve2dut&rid=giphy.gif&ct=g";
        when(gifService.getGifUrl(currencyService.getCurrencyRateStatus()))
                .thenReturn(gifUrl);
        GifDto gifDto = restController.getGifDto(currencyService.getCurrencyRateStatus());

        assertEquals("Checking gifDto", gifUrl, gifDto.getGifUrl());
    }
}
