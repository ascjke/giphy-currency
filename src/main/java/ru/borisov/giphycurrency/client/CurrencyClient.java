package ru.borisov.giphycurrency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currencyClient", url = "${openexchangerates.url}")
public interface CurrencyClient {

    @GetMapping("/latest.json")
    String getLatestExchangeRate(@RequestParam("app_id") String apiKey,
                                 @RequestParam("symbols") String currency);

    @GetMapping("/historical/{date}.json")
    String getYesterdayExchangeRate(@PathVariable("date") String yesterdayDate,
                                    @RequestParam("app_id") String apiKey,
                                 @RequestParam("symbols") String currency);
}
