package ru.borisov.giphycurrency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.borisov.giphycurrency.controller.CurrencyGifRestController;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.service.CurrencyService;
import ru.borisov.giphycurrency.service.GifService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CurrencyGifRestControllerTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    CurrencyGifRestController restController;
    @MockBean
    GifService gifService;
    @MockBean
    CurrencyService currencyService;

    @Test
    public void getCurrencyGifDtoTest() {

        String currencyStatus = "rich";
        String gifUrl = "https://media2.giphy.com/media/LdOyjZ7io5Msw/giphy.gif?cid=1eb9b1efaob49imm7n4betfs3na6jvlaulwxb4thzfbeotuy&rid=giphy.gif&ct=g";
        String currency = "rub";
        double yesterdayExchangeRate = 72.425;
        double latestExchangeRate = 71.375003;
        LocalDateTime yesterdayExchangeTime = LocalDateTime.parse("2022-12-22 08:59:30", formatter);
        LocalDateTime latestExchangeTime = LocalDateTime.parse("2022-12-22 09:59:51", formatter);

        CurrencyGifDto currencyGifDto = CurrencyGifDto.builder()
                .currencyStatus(currencyStatus)
                .currency(currency)
                .yesterdayExchangeRate(yesterdayExchangeRate)
                .latestExchangeRate(latestExchangeRate)
                .yesterdayExchangeTime(yesterdayExchangeTime)
                .latestExchangeTime(latestExchangeTime)
                .build();

        when(currencyService.getCurrencyGifDto()).thenReturn(currencyGifDto);
        when(gifService.getGifUrl(currencyService.getCurrencyGifDto().getCurrencyStatus())).thenReturn(gifUrl);

        ResponseEntity<CurrencyGifDto> expected = new ResponseEntity<>(currencyGifDto, HttpStatus.OK);
        ResponseEntity<CurrencyGifDto> actual = restController.getCurrencyGifDto();

        assertEquals("Checking rest api", expected, actual);
    }
}
