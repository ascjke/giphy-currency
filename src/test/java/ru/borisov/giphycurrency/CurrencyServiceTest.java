package ru.borisov.giphycurrency;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import ru.borisov.giphycurrency.client.CurrencyClient;
import ru.borisov.giphycurrency.dto.CurrencyGifDto;
import ru.borisov.giphycurrency.service.CurrencyService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CurrencyServiceTest {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static double[] yesterdayExchangeRates;
    static double[] latestExchangeRates;
    static String[] currencyStatuses;
    static String currency;
    static LocalDateTime yesterdayExchangeTime;
    static long yesterdayEpochSeconds = 1671699570L;
    static long latestEpochSeconds = 1671670791L;
    static LocalDateTime latestExchangeTime;
    static CurrencyGifDto currencyGifDto;
    static int index = 0;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    CacheManager cacheManager;

    @MockBean
    CurrencyClient currencyClient;

    @BeforeAll
    static void setUp() {
        yesterdayExchangeRates = new double[]{72.425, 70.5647, 68.4566};
        latestExchangeRates = new double[]{71.375003, 71.257, 68.4566};
        currencyStatuses = new String[]{"rich", "broke", "so-so"};
        currency = "rub";
        yesterdayExchangeTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(yesterdayEpochSeconds),
                TimeZone.getDefault().toZoneId());
        latestExchangeTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(latestEpochSeconds),
                TimeZone.getDefault().toZoneId());

        currencyGifDto = CurrencyGifDto.builder()
                .currency(currency)
                .yesterdayExchangeTime(yesterdayExchangeTime)
                .latestExchangeTime(latestExchangeTime)
                .build();
    }

    @AfterEach
    void cleanCache() {
        Objects.requireNonNull(cacheManager.getCache("latestExchangeRate")).clear();
        Objects.requireNonNull(cacheManager.getCache("yesterdayExchangeRate")).clear();
    }

    @RepeatedTest(value = 3, name = "Get currencyGifDto {currentRepetition}/{totalRepetitions}")
    public void getCurrencyGifDtoTest() {
        currencyGifDto.setYesterdayExchangeRate(yesterdayExchangeRates[index]);
        currencyGifDto.setLatestExchangeRate(latestExchangeRates[index]);
        currencyGifDto.setCurrencyStatus(currencyStatuses[index]);
        LocalDate yesterday = LocalDate.now().minusDays(1);

        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), currency))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": " + latestEpochSeconds + ",\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\":" + latestExchangeRates[index] + "\n" +
                        "  }\n" +
                        "}");

        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(), currencyService.getCurrencyApiKey(), currency))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": " + yesterdayEpochSeconds + ",\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\":" + yesterdayExchangeRates[index] + "\n" +
                        "  }\n" +
                        "}");

        CurrencyGifDto currencyGifDtoActual = currencyService.getCurrencyGifDto();
        assertEquals("Checking currencyGifDto:", currencyGifDto, currencyGifDtoActual);
        index++;
    }


    @Test
    @DisplayName("The latest exchange rate of RUB should be 70.2499997")
    public void getLatestExchangeRateTest() {
        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), currency))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671584392,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 70.249997\n" +
                        "  }\n" +
                        "}");

        double expected = 70.249997;
        CompletableFuture<Double> actual = currencyService.getLatestExchangeRate();

        assertEquals("Checking latest exchange rate:", expected, actual.join());
    }

    @Test
    @DisplayName("The yesterday exchange rate of RUB should be 68.499901")
    public void getYesterdayExchangeRateTest() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(),
                currencyService.getCurrencyApiKey(), currency))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671580792,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 68.499901\n" +
                        "  }\n" +
                        "}");

        double expected = 68.499901;
        CompletableFuture<Double> actual = currencyService.getYesterdayExchangeRate();

        assertEquals("Checking yesterday exchange rate:", expected, actual.join());
    }
}
