package ru.borisov.giphycurrency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.borisov.giphycurrency.client.CurrencyClient;
import ru.borisov.giphycurrency.service.CurrencyService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    CurrencyService currencyService;
    @MockBean
    CurrencyClient currencyClient;

    @Test
    @DisplayName("The latest exchange rate of RUB should be 70.2499997")
    public void getLatestExchangeRateTest() {
        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), "rub"))
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
        double actual = currencyService.getLatestExchangeRate();

        assertEquals("Checking latest exchange rate:", expected, actual);
    }

    @Test
    @DisplayName("The yesterday exchange rate of RUB should be 68.499901")
    public void getYesterdayExchangeRateTest() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(),
                currencyService.getCurrencyApiKey(), "rub"))
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
        double actual = currencyService.getYesterdayExchangeRate();

        assertEquals("Checking yesterday exchange rate:", expected, actual);
    }

    @Test
    @DisplayName("If yesterday 1 USD was 68.499901 RUB, today 1 USD is 70.249997 RUB, status should be \"broke\"")
    public void getBrokeCurrencyRateStatusTest() {
        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671584392,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 70.249997\n" +
                        "  }\n" +
                        "}");

        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(),
                currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671580792,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 68.499901\n" +
                        "  }\n" +
                        "}");

        String expected = "broke";
        String actual = currencyService.getCurrencyRateStatus();
        assertEquals("Checking currency rate status:", expected, actual);
    }

    @Test
    @DisplayName("If yesterday 1 USD was 70.249997 RUB, today 1 USD is 68.499901 RUB, status should be \"rich\"")
    public void getRichCurrencyRateStatusTest() {
        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671584392,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 68.499901\n" +
                        "  }\n" +
                        "}");

        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(),
                currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671580792,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 70.249997\n" +
                        "  }\n" +
                        "}");

        String expected = "rich";
        String actual = currencyService.getCurrencyRateStatus();
        assertEquals("Checking currency rate status:", expected, actual);
    }

    @Test
    @DisplayName("If yesterday 1 USD was 70.249997 RUB, today 1 USD is 70.249997 RUB, status should be \"so-so\"")
    public void getSosoCurrencyRateStatusTest() {
        when(currencyClient.getLatestExchangeRate(currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671584392,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 70.249997\n" +
                        "  }\n" +
                        "}");

        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(currencyClient.getYesterdayExchangeRate(yesterday.toString(),
                currencyService.getCurrencyApiKey(), "rub"))
                .thenReturn("{\n" +
                        "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                        "  \"license\": \"https://openexchangerates.org/license\",\n" +
                        "  \"timestamp\": 1671580792,\n" +
                        "  \"base\": \"USD\",\n" +
                        "  \"rates\": {\n" +
                        "    \"RUB\": 70.249997\n" +
                        "  }\n" +
                        "}");

        String expected = "so-so";
        String actual = currencyService.getCurrencyRateStatus();
        assertEquals("Checking currency rate status:", expected, actual);
    }
}
