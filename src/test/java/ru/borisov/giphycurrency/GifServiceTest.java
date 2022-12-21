package ru.borisov.giphycurrency;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.borisov.giphycurrency.client.GifClient;
import ru.borisov.giphycurrency.service.GifService;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class GifServiceTest {

    static String[] currencyRateStatuses;
    static String[] gifUrls;
    static int index = 0;

    @Autowired
    GifService gifService;

    @MockBean
    GifClient gifClient;

    @BeforeAll
    static void setUp() {
        currencyRateStatuses = new String[] {"rich", "broke", "so-so"};
        gifUrls = new String[]
                {"https://media1.giphy.com/media/LdOyjZ7io5Msw/giphy.gif?cid=1eb9b1efexdppscjglhe3xwsj5e1benua7zpego5cfve2dut&rid=giphy.gif&ct=g", // 0 - rich
                "https://media2.giphy.com/media/ZGH8VtTZMmnwzsYYMf/giphy.gif?cid=1eb9b1efvh9v8s0n3mhk1kmyzhasivu6g7l82vu830bkbt85&rid=giphy.gif&ct=g", // 1 - broke
                "https://media2.giphy.com/media/Lk023zZqHJ3Zz4rxtV/giphy.gif?cid=1eb9b1efppgdv9xwqgoiarhg44qjv9e4oe6wddcuenaui98l&rid=giphy.gif&ct=g"}; // 2 -so-so
    }

    @AfterEach
    void incrementIndex() {
        index++;
    }

    @RepeatedTest(value = 3, name = "Get gif url {currentRepetition}/{totalRepetitions}")
    public void getGifUrlTest() {
        when(gifClient.getGifResponse(gifService.getGifApiKey(), currencyRateStatuses[index]))
                .thenReturn("{\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"images\": {\n" +
                        "        \"original\": {\n" +
                        "          \"height\": \"374\",\n" +
                        "          \"width\": \"500\",\n" +
                        "          \"size\": \"466377\",\n" +
                        "          \"url\": \"" + gifUrls[index] + "\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}    ");

        String expected = gifUrls[index];
        String actual = gifService.getGifUrl(currencyRateStatuses[index]);

        assertEquals("Checking for getting \"" + currencyRateStatuses[index] + "\" gif URL:", expected, actual);
    }
}
